package io.github.kydzombie.monke.event.ingame;

import io.github.kydzombie.monke.Monke;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.entity.player.PlayerHandler;

public class MonkePlayerHandler implements PlayerHandler {
    private final PlayerEntity player;
    private int totalCrafts = 0;

    public MonkePlayerHandler(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public boolean readEntityBaseFromNBT(NbtCompound nbt) {
        totalCrafts = nbt.getInt("monke:total_crafts");
        return false;
    }

    @Override
    public boolean writeEntityBaseToNBT(NbtCompound nbt) {
        nbt.putInt("monke:total_crafts", totalCrafts);
        return false;
    }

    public void addCraft() {
        totalCrafts++;
        Monke.LOGGER.debug(player.name + "'s crafts is now " + totalCrafts + 'n');
    }
}
