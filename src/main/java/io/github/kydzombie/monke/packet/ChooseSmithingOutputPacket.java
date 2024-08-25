package io.github.kydzombie.monke.packet;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.event.MonkeToolRegistry;
import io.github.kydzombie.monke.gui.screen.SmithingAnvilScreenHandler;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ChooseSmithingOutputPacket extends Packet implements IdentifiablePacket {
    String choice;

    public ChooseSmithingOutputPacket() {
    }

    public ChooseSmithingOutputPacket(String choice) {
        this.choice = choice;
    }

    @Override
    public Identifier getId() {
        return Monke.NAMESPACE.id("choose_smithing_output");
    }

    @Override
    public void read(DataInputStream stream) {
        choice = readString(stream, 32);
    }

    @Override
    public void write(DataOutputStream stream) {
        writeString(choice, stream);
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        var player = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if (player.container instanceof SmithingAnvilScreenHandler screen) {
            var blockEntity = screen.smithingAnvilBlockEntity;
            var part = MonkeToolRegistry.parts.get(choice);
            if (part != null) {
                blockEntity.setSelectedPart(part);
            } else {
                Monke.LOGGER.error("Part name {} is invalid.", choice);
            }
        }
    }

    @Override
    public int size() {
        return choice.length() + 1;
    }
}
