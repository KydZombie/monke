package io.github.kydzombie.monke.event.init;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.packet.ChooseSmithingOutputPacket;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;

public class MonkePackets {
    @EventListener
    private void registerPackets(PacketRegisterEvent event) {
        IdentifiablePacket.register(Monke.NAMESPACE.id("choose_smithing_output"), false, true, ChooseSmithingOutputPacket::new);
    }
}
