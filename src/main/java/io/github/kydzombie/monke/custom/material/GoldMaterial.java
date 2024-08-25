package io.github.kydzombie.monke.custom.material;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.block.BlockState;

public class GoldMaterial extends MonkeMaterial {
    public GoldMaterial(String name, ToolMaterial toolMaterial, int color, CreationMethod creationMethod, ItemStack materialItem, float percentDurabilityBoost) {
        super(name, toolMaterial, color, creationMethod, materialItem, percentDurabilityBoost);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state, NbtCompound partNbt, int partIndex) {
        return partIndex == 0 ? 1f : 1.2f;
    }
}
