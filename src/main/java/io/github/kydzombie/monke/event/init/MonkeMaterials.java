package io.github.kydzombie.monke.event.init;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.custom.material.CreationMethod;
import io.github.kydzombie.monke.custom.material.EnergizedWoodMaterial;
import io.github.kydzombie.monke.custom.material.GoldMaterial;
import io.github.kydzombie.monke.custom.material.MonkeMaterial;
import io.github.kydzombie.monke.event.MonkeMaterialRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.registry.AfterBlockAndItemRegisterEvent;
import net.modificationstation.stationapi.api.item.tool.ToolMaterialFactory;

public class MonkeMaterials {
    @EventListener
    private void callMaterialEvent(AfterBlockAndItemRegisterEvent event) {
        Monke.LOGGER.info("Invoking MonkeMaterialRegistry event...");
        StationAPI.EVENT_BUS.post(new MonkeMaterialRegistryEvent());
    }

    @EventListener
    private void registerMaterials(MonkeMaterialRegistryEvent event) {
        new MonkeMaterial("wood", ToolMaterial.WOOD, 0x966F33, CreationMethod.WOOD_WORKING, new ItemStack(Block.PLANKS), 1f);
        new MonkeMaterial("stone", ToolMaterial.STONE, 0x444444, CreationMethod.KNAPPING, new ItemStack(Block.COBBLESTONE), 0.75f);
        new MonkeMaterial("iron", ToolMaterial.IRON, 0xFFFFFF, CreationMethod.SMITHING, new ItemStack(Item.IRON_INGOT), 1.1f);
        new GoldMaterial("gold", ToolMaterial.GOLD, 0xFFBF00, CreationMethod.SMITHING, new ItemStack(Item.GOLD_INGOT), 0.9f);

        new EnergizedWoodMaterial("energized_wood", ToolMaterialFactory.create("monke:energized_wood", 2, 8, 10.0f, 1).inheritsFrom(ToolMaterial.IRON), 0xB0643A, CreationMethod.WOOD_WORKING, new ItemStack(MonkeItems.energizedWoodShard), 0.8f);
    }
}
