package io.github.kydzombie.monke.custom.material;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.event.MonkeMaterialRegistry;
import io.github.kydzombie.monke.item.tool.MonkeToolItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.block.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class MonkeMaterial {
    public final String name;
    public final ToolMaterial toolMaterial;
    public final int color;
    public final CreationMethod creationMethod;
    public final ItemStack materialItem;
    public final float durabilityBoost;

    public MonkeMaterial(String name, ToolMaterial toolMaterial, int color, CreationMethod creationMethod, ItemStack materialItem, float percentDurabilityBoost) {
        this.name = name;
        this.toolMaterial = toolMaterial;
        this.color = color;
        this.creationMethod = creationMethod;
        this.materialItem = materialItem;
        this.durabilityBoost = percentDurabilityBoost;

        MonkeMaterialRegistry.registerMaterial(this);
    }

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state, NbtCompound partNbt, int partIndex) {
        return 1f;
    }

    public void postMine(ItemStack stack, int blockId, int x, int y, int z, LivingEntity miner, int partIndex) {
    }

    public void inventoryTick(@Nullable Entity holder, ItemStack stack, NbtCompound partNbt, int partIndex, boolean held) {
    }

    public void inventoryTick(ItemStack stack, NbtCompound partNbt, int partIndex) {
        inventoryTick(null, stack, partNbt, partIndex, false);
    }

    public int getNewDurability(ItemStack stack, NbtCompound partNbt, int partIndex, int currentDurability) {
        if (durabilityBoost == 1.0 || partIndex == 0) return currentDurability;
        var tool = (MonkeToolItem) stack.getItem();
        var parts = tool.parts;
        var part = parts[partIndex];
        if (Arrays.stream(parts).noneMatch((partOnTool) -> partOnTool == part)) {
            Monke.LOGGER.error("Attempted to get durability of " + stack + " with part " + part + " but part doesn't exist!");
            return currentDurability;
        }
        return (int) (currentDurability * durabilityBoost);
    }
}
