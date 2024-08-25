package io.github.kydzombie.monke.compat;

import com.matthewperiut.spc.api.Command;
import com.matthewperiut.spc.api.CommandRegistry;
import com.matthewperiut.spc.util.SharedCommandSource;
import io.github.kydzombie.monke.event.ingame.MonkePlayerHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.impl.entity.player.PlayerAPI;

public class MonkeSPCCompat {
    @EventListener
    private void registerCommands(InitEvent event) {
        if (!FabricLoader.getInstance().isModLoaded("spc")) return;

        CommandRegistry.add(new Command() {
            @Override
            public void command(SharedCommandSource commandSource, String[] parameters) {
                if (parameters.length < 2) {
                    commandSource.sendFeedback("Must specify an option: stats");
                    return;
                }

                switch (parameters[1]) {
                    case "stats":
                        var stats = (MonkePlayerHandler) PlayerAPI.getPlayerHandler(commandSource.getPlayer(), MonkePlayerHandler.class);
                        commandSource.sendFeedback("You have crafted " + stats.getTotalCrafts() + " items.");
                        break;
                    default:
                        commandSource.sendFeedback("Unknown option: " + parameters[1]);
                        break;
                }
            }

            @Override
            public String name() {
                return "monke";
            }

            @Override
            public void manual(SharedCommandSource commandSource) {

            }
        });
    }
}
