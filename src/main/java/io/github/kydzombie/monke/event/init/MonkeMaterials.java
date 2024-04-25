package io.github.kydzombie.monke.event.init;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.event.MonkeMaterialRegistryEvent;
import io.github.kydzombie.monke.material.CreationMethod;
import io.github.kydzombie.monke.material.MonkeMaterial;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.registry.AfterBlockAndItemRegisterEvent;

public class MonkeMaterials {
    @EventListener
    private void callMaterialEvent(AfterBlockAndItemRegisterEvent event) {
        Monke.LOGGER.info("Invoking MonkeMaterialRegistry event...");
        StationAPI.EVENT_BUS.post(new MonkeMaterialRegistryEvent());
    }
    @EventListener
    private void registerMaterials(MonkeMaterialRegistryEvent event) {
        event.register(new MonkeMaterial("wood", ToolMaterial.WOOD, 0x966F33, CreationMethod.WOOD_WORKING, new ItemStack(Block.PLANKS)));
        event.register(new MonkeMaterial("stone", ToolMaterial.STONE, 0x444444, CreationMethod.KNAPPING, new ItemStack(Block.COBBLESTONE)));
        event.register(new MonkeMaterial("iron", ToolMaterial.IRON, 0xFFFFFF, CreationMethod.SMITHING, new ItemStack(Item.IRON_INGOT)));
        event.register(new MonkeMaterial("gold", ToolMaterial.GOLD, 0xFFBF00, CreationMethod.SMITHING, new ItemStack(Item.GOLD_INGOT)));
    }
}
