package io.github.kydzombie.monke.gui.screen;

import io.github.kydzombie.monke.block.entity.SmithingAnvilBlockEntity;
import io.github.kydzombie.monke.gui.screen.slot.SmithingAnvilOutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SmithingAnvilScreenHandler extends ScreenHandler {
    public final PlayerEntity player;
    public final SmithingAnvilBlockEntity smithingAnvilBlockEntity;

    public SmithingAnvilScreenHandler(PlayerInventory playerInventory, SmithingAnvilBlockEntity smithingAnvilBlockEntity) {
        this.player = playerInventory.player;
        this.smithingAnvilBlockEntity = smithingAnvilBlockEntity;

        this.addSlot(new Slot(smithingAnvilBlockEntity, 0, 27, 22));
        this.addSlot(new Slot(smithingAnvilBlockEntity, 1, 76, 22));
        this.addSlot(new SmithingAnvilOutputSlot(playerInventory.player, smithingAnvilBlockEntity, 2, 134, 22));

        int var3;
        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlot(new Slot(playerInventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlot(new Slot(playerInventory, var3, 8 + var3 * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return smithingAnvilBlockEntity.canPlayerUse(player);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        smithingAnvilBlockEntity.currentPlayer = null;
    }

//    @Override
//    public ItemStack getStackInSlot(int slotIndex) {
//        if (slotIndex != 2) return super.getStackInSlot(slotIndex);
//
//        ItemStack stack = super.getStackInSlot(slotIndex);
//        Slot slot = (Slot) this.slots.get(slotIndex);
//        smithingAnvilBlockEntity.onCraft(stack);
//        slot.onCrafted(stack);
//        var stats = (MonkePlayerHandler) PlayerAPI.getPlayerHandler(player, MonkePlayerHandler.class);
//        stats.addCraft();
//
//        return stack;
//    }
}
