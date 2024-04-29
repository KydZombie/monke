package io.github.kydzombie.monke.event.init.client;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.event.MonkeToolRegistry;
import io.github.kydzombie.monke.event.init.MonkeItems;
import io.github.kydzombie.monke.item.MonkeToolItem;
import io.github.kydzombie.monke.item.ToolPartItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.AfterBlockAndItemRegisterEvent;

import java.util.HashMap;

public class MonkeToolRendering {

    private static final HashMap<MonkeToolItem, ToolPartItem[]> renderingOrders = new HashMap<>();

    @EventListener
    private void setToolRenderingOrder(AfterBlockAndItemRegisterEvent event) {
        Monke.LOGGER.info("Setting item rendering order...");
        // Vanilla Tools
        renderingOrders.put(MonkeItems.pickaxe, new ToolPartItem[]{MonkeItems.toolHandle, MonkeItems.pickaxeHead});
        renderingOrders.put(MonkeItems.axe, new ToolPartItem[]{MonkeItems.toolHandle, MonkeItems.axeHead});
        renderingOrders.put(MonkeItems.shovel, new ToolPartItem[]{MonkeItems.toolHandle, MonkeItems.shovelHead});
        renderingOrders.put(MonkeItems.hoe, new ToolPartItem[]{MonkeItems.toolHandle, MonkeItems.hoeHead});
        renderingOrders.put(MonkeItems.sword, new ToolPartItem[]{MonkeItems.toolHandle, MonkeItems.swordBlade, MonkeItems.swordGuard});

        // Extended Tools
        renderingOrders.put(MonkeItems.hammer, new ToolPartItem[]{MonkeItems.toolHandle, MonkeItems.hammerHead});
        renderingOrders.put(MonkeItems.saw, new ToolPartItem[]{MonkeItems.toolHandle, MonkeItems.sawBlade});
    }

    @EventListener
    private void registerItemColors(ItemColorsRegisterEvent event) {
        Monke.LOGGER.info("Registering item tinting...");

        event.itemColors.register((itemStack, tintIndex) -> {
            var material = ToolPartItem.getMonkeMaterial(itemStack);
            return material != null ? material.color : 0;
        }, MonkeToolRegistry.parts.toArray(ToolPartItem[]::new));

        event.itemColors.register((itemStack, tintIndex) -> {
            var tool = itemStack.getItem();
            assert (tool instanceof MonkeToolItem);
            var part = renderingOrders.get(tool);
            if (part == null || part.length < tintIndex) return 0;
            var material = MonkeToolItem.getPartMaterial(itemStack, part[tintIndex]);
            return material != null ? material.color : 0;
        }, MonkeToolRegistry.tools.toArray(MonkeToolItem[]::new));
    }
}
