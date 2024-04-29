package io.github.kydzombie.monke.block.entity;

import io.github.kydzombie.monke.event.MonkeMaterialRegistry;
import io.github.kydzombie.monke.event.init.MonkeItems;
import io.github.kydzombie.monke.item.MonkeToolItem;
import io.github.kydzombie.monke.item.ToolPartItem;
import io.github.kydzombie.monke.material.CreationMethod;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class SmithingAnvilBlockEntity extends BlockEntity implements Inventory {
    private ToolPartItem selectedPart = null;
    private ItemStack[] inventory = new ItemStack[3];
    private static final int OUTPUT_SLOT = 2;

    @Override
    public int size() {
        return inventory.length;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory[slot];
    }

    @Override
    public void method_1076() {
        for (var stack : inventory) {
            if (stack == null) return;
            if (stack.getItem() instanceof MonkeToolItem tool) {
                var nbt = stack.getStationNbt();
                var partsNbt = nbt.getCompound("monke_parts");
                for (int i = 0; i < tool.parts.length; i++) {
                    var part = tool.parts[i];
                    var partNbt = partsNbt.getCompound(part.getTranslationKey());
                    var material = MonkeToolItem.getPartMaterialFromNbt(partNbt);
                    if (material == null) continue;
                    material.inventoryTick(stack, nbt, i);
                }
            }
        }
    }

    void onCraft(ItemStack crafted) {
        for (int i = 0; i < inventory.length - 1; i++) {
            ItemStack item = inventory[i];
            if (item == null) return;
            if (item.getMaxDamage() != 0) {
                item.damage(1, null);
            } else {
                item.count--;
                if (item.count <= 0) {
                    inventory[i] = null;
                }
            }
        }
        markDirty();
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (this.inventory[slot] != null) {
            ItemStack taken;
            if (inventory[slot].count <= amount) {
                taken = inventory[slot];
                inventory[slot] = null;
            } else {
                taken = inventory[slot].split(amount);
                if (inventory[slot].count == 0) {
                    inventory[slot] = null;
                }
            }
            if (slot == OUTPUT_SLOT) onCraft(taken);

            return taken;
        } else {
            return null;
        }
    }

    // TODO: Make this less stupid
    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory[slot] = stack;
        if (stack != null && stack.count > this.getMaxCountPerStack()) {
            stack.count = this.getMaxCountPerStack();
        }
        if (slot == OUTPUT_SLOT) onCraft(stack);
    }

    @Override
    public String getName() {
        return "Smithing Anvil";
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.method_1777(this.x, this.y, this.z) != this) {
            return false;
        } else {
            return !(player.method_1347((double) this.x + 0.5, (double) this.y + 0.5, (double) this.z + 0.5) > 64.0);
        }
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        NbtList itemList = nbt.getList("items");
        this.inventory = new ItemStack[this.size()];

        for (int var3 = 0; var3 < itemList.size(); ++var3) {
            NbtCompound var4 = (NbtCompound) itemList.get(var3);
            byte var5 = var4.getByte("slot");
            if (var5 >= 0 && var5 < this.inventory.length) {
                this.inventory[var5] = new ItemStack(var4);
            }
        }
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        NbtList itemList = new NbtList();

        for (int var3 = 0; var3 < this.inventory.length; ++var3) {
            if (this.inventory[var3] != null) {
                NbtCompound var4 = new NbtCompound();
                var4.putByte("slot", (byte) var3);
                this.inventory[var3].writeNbt(var4);
                itemList.add(var4);
            }
        }

        nbt.put("items", itemList);
    }

    @Override
    public void markDirty() {
        if (selectedPart == null || inventory[0] == null || inventory[1] == null) {
            inventory[OUTPUT_SLOT] = null;
            return;
        }

        var material = MonkeMaterialRegistry.getMaterialFromCraftingMaterial(inventory[1]);
        if (material == null) {
            inventory[OUTPUT_SLOT] = null;
            return;
        }

        if ((material.creationMethod == CreationMethod.SMITHING && inventory[0].getItem() != MonkeItems.hammer) ||
                (material.creationMethod == CreationMethod.WOOD_WORKING && inventory[0].getItem() != MonkeItems.saw)) {
            inventory[OUTPUT_SLOT] = null;
            return;
        }

        var outputItem = new ItemStack(selectedPart);
        ToolPartItem.setMonkeMaterial(outputItem, material);

        inventory[OUTPUT_SLOT] = outputItem;

        super.markDirty();
    }

    public ToolPartItem getSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(ToolPartItem selectedPart) {
        this.selectedPart = selectedPart;
        markDirty();
    }
}
