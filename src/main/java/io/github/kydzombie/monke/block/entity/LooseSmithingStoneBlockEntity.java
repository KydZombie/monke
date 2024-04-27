package io.github.kydzombie.monke.block.entity;

import io.github.kydzombie.monke.event.init.MonkeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;

public class LooseSmithingStoneBlockEntity extends BlockEntity {
    private static final int SETTING_TIME = 2 * 60 * 20; // 2 minutes
    private int time_until_set = 0;

    @Override
    public void method_1076() {
        if (world.getBlockId(x + 1, y, z) == Block.WATER.id || world.getBlockId(x + 1, y, z) == Block.FLOWING_WATER.id ||
                world.getBlockId(x - 1, y, z) == Block.WATER.id || world.getBlockId(x - 1, y, z) == Block.FLOWING_WATER.id ||
                world.getBlockId(x, y + 1, z) == Block.WATER.id || world.getBlockId(x, y + 1, z) == Block.FLOWING_WATER.id ||
                world.getBlockId(x, y - 1, z) == Block.WATER.id || world.getBlockId(x, y - 1, z) == Block.FLOWING_WATER.id ||
                world.getBlockId(x, y, z + 1) == Block.WATER.id || world.getBlockId(x, y, z + 1) == Block.FLOWING_WATER.id ||
                world.getBlockId(x, y, z - 1) == Block.WATER.id || world.getBlockId(x, y, z - 1) == Block.FLOWING_WATER.id) {
            time_until_set++;
            if (time_until_set >= SETTING_TIME) {
                world.setBlock(x, y, z, MonkeBlocks.smithingStone.id);
            }
        } else {
            time_until_set = 0;
        }
    }
}
