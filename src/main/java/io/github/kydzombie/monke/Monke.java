package io.github.kydzombie.monke;

import io.github.kydzombie.monke.event.ingame.MonkePlayerHandler;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.player.PlayerEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class Monke {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    @Entrypoint.Logger
    public static final Logger LOGGER = Null.get();

    @EventListener
    private void registerPlayerHandlers(PlayerEvent.HandlerRegister event) {
        event.playerHandlers.add(new MonkePlayerHandler(event.player));
    }
}
