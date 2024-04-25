package io.github.kydzombie.monke.material;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public record MonkeMaterial(String name, ToolMaterial toolMaterial, int color, CreationMethod creationMethod, ItemStack materialItem) {}
