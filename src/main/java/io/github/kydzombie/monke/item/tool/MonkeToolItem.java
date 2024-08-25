package io.github.kydzombie.monke.item.tool;

import io.github.kydzombie.monke.custom.material.MonkeMaterial;
import io.github.kydzombie.monke.event.MonkeMaterialRegistry;
import io.github.kydzombie.monke.event.MonkeToolRegistry;
import io.github.kydzombie.monke.item.ToolPartItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.item.tool.ToolMaterialFactory;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.template.item.TemplateToolItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

public class MonkeToolItem extends TemplateToolItem implements CustomTooltipProvider {
    private static final ToolMaterial MISSING_MATERIAL =
            ToolMaterialFactory.create("monke:missing_tool_material", 0, 0, 0, 0);

    public final ToolPartItem[] parts;

    public MonkeToolItem(Identifier identifier, int damageBoost, TagKey<Block> effectiveBlocks, ToolPartItem[] parts) {
        super(identifier, damageBoost, MISSING_MATERIAL, new Block[]{});
        this.parts = parts;
        setEffectiveBlocks(effectiveBlocks);
        setTranslationKey(identifier);
        MonkeToolRegistry.tools.add(this);
    }

    public static @Nullable MonkeMaterial getPartMaterialFromNbt(NbtCompound partNbt) {
        return MonkeMaterialRegistry.getMaterial(partNbt.getString("material"));
    }

    public static @Nullable MonkeMaterial getPartMaterial(ItemStack stack, ToolPartItem part) {
        var nbt = stack.getStationNbt();
        var partsNbt = nbt.getCompound("monke_parts");
        var partNbt = partsNbt.getCompound(ItemRegistry.INSTANCE.getId(part.id).orElseThrow().toString());
        return getPartMaterialFromNbt(partNbt);
    }

    public static void setMonkePart(@NotNull ItemStack stack, @NotNull ItemStack part) {
        var nbt = stack.getStationNbt();
        var partsNbt = nbt.getCompound("monke_parts");
        partsNbt.put(ItemRegistry.INSTANCE.getId(part.itemId).orElseThrow().toString(), part.getStationNbt().getCompound("monke_data").copy());
        nbt.put("monke_parts", partsNbt);
    }

    public ToolMaterial getMaterial(ItemStack stack) {
        var primaryMaterial = getPartMaterial(stack, parts[0]);
        return primaryMaterial != null ? primaryMaterial.toolMaterial : MISSING_MATERIAL;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        var durability = getMaterial(stack).getDurability();
        var nbt = stack.getStationNbt();
        var partsNbt = nbt.getCompound("monke_parts");
        for (int i = 0; i < parts.length; i++) {
            var part = parts[i];
            var partNbt = partsNbt.getCompound(ItemRegistry.INSTANCE.getId(part.id).orElseThrow().toString());
            var material = getPartMaterialFromNbt(partNbt);
            if (material == null) continue;
            durability = material.getNewDurability(stack, partNbt, i, durability);
        }
        return durability;
    }

    @Override
    public boolean postMine(ItemStack stack, int blockId, int x, int y, int z, LivingEntity miner) {
        var nbt = stack.getStationNbt();
        var partsNbt = nbt.getCompound("monke_parts");
        for (int i = 0; i < parts.length; i++) {
            var part = parts[i];
            var partNbt = partsNbt.getCompound(ItemRegistry.INSTANCE.getId(part.id).orElseThrow().toString());
            var material = getPartMaterialFromNbt(partNbt);
            if (material == null) continue;
            material.postMine(stack, blockId, x, y, z, miner, i);
        }
        return super.postMine(stack, blockId, x, y, z, miner);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        var nbt = stack.getStationNbt();
        var partsNbt = nbt.getCompound("monke_parts");
        for (int i = 0; i < parts.length; i++) {
            var part = parts[i];
            var partNbt = partsNbt.getCompound(ItemRegistry.INSTANCE.getId(part.id).orElseThrow().toString());
            var material = getPartMaterialFromNbt(partNbt);
            if (material == null) continue;
            material.inventoryTick(entity, stack, nbt, i, selected);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        ArrayList<String> tooltip = new ArrayList<>();
        tooltip.add(originalTooltip);
        var nbt = stack.getStationNbt();
        var partsNbt = nbt.getCompound("monke_parts");
        for (int i = 0; i < parts.length; i++) {
            var part = parts[i];
            var partNbt = partsNbt.getCompound(ItemRegistry.INSTANCE.getId(part.id).orElseThrow().toString());
            var material = MonkeToolItem.getPartMaterialFromNbt(partNbt);
            tooltip.add(ItemRegistry.INSTANCE.getId(part.id).orElseThrow() + ": " + (material != null ? material.name : "none"));
            var buffs = partNbt.getList("buffs");
            for (int j = 0; j < buffs.size(); j++) {
                var buffNbt = (NbtCompound) buffs.get(j);
                if (Objects.equals(buffNbt.getString("type"), "mining_speed")) {
                    tooltip.add("  Mining Speed: " + buffNbt.getFloat("speed"));
                }
            }
        }
        var durability = stack.getMaxDamage();
        tooltip.add("D: " + (durability - stack.getDamage()) + '/' + durability);
        return tooltip.toArray(String[]::new);
    }
}
