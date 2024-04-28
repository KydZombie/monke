package io.github.kydzombie.monke.event;

import io.github.kydzombie.monke.material.MonkeMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;

public class MonkeMaterialRegistry {
    protected static final HashMap<String, MonkeMaterial> materials = new HashMap<>();

    public static void registerMaterial(MonkeMaterial material) {
        MonkeMaterialRegistry.materials.put(material.name, material);
    }

    public static MonkeMaterial getMaterial(String name) {
        return materials.get(name);
    }

    public static @Nullable MonkeMaterial getMaterialFromCraftingMaterial(ItemStack item) {
        for (var material : materials.values()) {
            if (material.materialItem.isItemEqual(item)) return material;
        }
        return null;
    }

    public static HashMap<String, MonkeMaterial> getAllMaterials() {
        return materials;
    }

    public static int getMaterialCount() {
        return materials.size();
    }
}
