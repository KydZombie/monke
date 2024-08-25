package io.github.kydzombie.monke.gui.screen.slot;

import io.github.kydzombie.monke.block.entity.SmithingAnvilBlockEntity;
import io.github.kydzombie.monke.event.ingame.MonkePlayerHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.modificationstation.stationapi.impl.entity.player.PlayerAPI;

public class SmithingAnvilOutputSlot extends Slot {
    private final SmithingAnvilBlockEntity smithingAnvilBlockEntity;
    private final PlayerEntity player;

    public SmithingAnvilOutputSlot(PlayerEntity player, SmithingAnvilBlockEntity smithingAnvilBlockEntity, int index, int x, int y) {
        super(smithingAnvilBlockEntity, index, x, y);
        this.player = player;
        this.smithingAnvilBlockEntity = smithingAnvilBlockEntity;
    }

    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public void onCrafted(ItemStack stack) {
        var stats = (MonkePlayerHandler) PlayerAPI.getPlayerHandler(player, MonkePlayerHandler.class);
        stats.addCraft();
        smithingAnvilBlockEntity.onCraft(stack);
        super.onCrafted(stack);
    }
}
