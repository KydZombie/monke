package io.github.kydzombie.monke.compat;

import io.github.kydzombie.monke.event.MonkeMaterialRegistry;
import io.github.kydzombie.monke.event.MonkeToolRegistry;
import io.github.kydzombie.monke.item.ToolPartItem;
import net.glasslauncher.hmifabric.Utils;
import net.glasslauncher.hmifabric.event.HMIItemListRefreshEvent;
import net.glasslauncher.hmifabric.event.HMITabRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.registry.AfterBlockAndItemRegisterEvent;

import java.util.ArrayList;

public class MonkeHMICompat implements HMIItemListRefreshEvent {

    @EventListener
    public static void addItems(AfterBlockAndItemRegisterEvent event) {
    }

    @EventListener
    private void registerTabsAndHideItems(HMITabRegistryEvent event) {
        for (var tool : MonkeToolRegistry.tools) {
            Utils.hiddenItems.add(new ItemStack(tool));
        }
        for (var part : MonkeToolRegistry.parts) {
            Utils.hiddenItems.add(new ItemStack(part));
        }
    }

    @Override
    public void refreshItemList(ArrayList<ItemStack> arrayList) {
        for (var part : MonkeToolRegistry.parts) {
            for (var material : MonkeMaterialRegistry.getAllMaterials().values()) {
                var newItem = new ItemStack(part);
                ToolPartItem.setMonkeMaterial(newItem, material);
                Utils.addItemInOrder(arrayList, newItem);
            }
        }
    }
}
