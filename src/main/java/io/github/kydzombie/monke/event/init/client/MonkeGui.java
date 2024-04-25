package io.github.kydzombie.monke.event.init.client;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.block.entity.SmithingAnvilBlockEntity;
import io.github.kydzombie.monke.gui.screen.ingame.SmithingAnvilScreen;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class MonkeGui {
    @EventListener
    private void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        event.registry.registerValueNoMessage(Monke.NAMESPACE.id("smithing_anvil"), BiTuple.of(this::openSmithingAnvil, SmithingAnvilBlockEntity::new));
    }

    public Screen openSmithingAnvil(PlayerEntity player, Inventory inventory) {
        return new SmithingAnvilScreen(player.inventory, (SmithingAnvilBlockEntity) inventory);
    }
}
