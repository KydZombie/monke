package io.github.kydzombie.monke.block.entity;

import io.github.kydzombie.monke.event.init.MonkeBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.nbt.NbtCompound;

public class LooseSmithingStoneBlockEntity extends BlockEntity {
    private static final String SET_TIME_KEY = "monke:set_time";
    private static final int SET_TIME = 2 * 60 * 20; // 2 minutes
    private int setTimeTicks = 0;

    private boolean isFloating() {
        int blockId = world.getBlockId(x, y - 1, z);
        if (blockId == 0) {
            return true;
            // TODO: Remove hardcoded values
        } else if (blockId == Block.FIRE.id) {
            return true;
        } else {
            Material var5 = Block.BLOCKS[blockId].material;
            if (var5 == Material.WATER) {
                return true;
            } else {
                return var5 == Material.LAVA;
            }
        }
    }

    @Override
    public void tick() {
        if (isFloating() && y >= 0) {
            FallingBlockEntity entity = new FallingBlockEntity(world, x + 0.5F, y + 0.5F, z + 0.5F, getBlock().id);
            world.method_210(entity);
            return;
        }
        if (world.getBlockId(x + 1, y, z) == Block.WATER.id || world.getBlockId(x + 1, y, z) == Block.FLOWING_WATER.id ||
                world.getBlockId(x - 1, y, z) == Block.WATER.id || world.getBlockId(x - 1, y, z) == Block.FLOWING_WATER.id ||
                world.getBlockId(x, y + 1, z) == Block.WATER.id || world.getBlockId(x, y + 1, z) == Block.FLOWING_WATER.id ||
                world.getBlockId(x, y - 1, z) == Block.WATER.id || world.getBlockId(x, y - 1, z) == Block.FLOWING_WATER.id ||
                world.getBlockId(x, y, z + 1) == Block.WATER.id || world.getBlockId(x, y, z + 1) == Block.FLOWING_WATER.id ||
                world.getBlockId(x, y, z - 1) == Block.WATER.id || world.getBlockId(x, y, z - 1) == Block.FLOWING_WATER.id) {
            setTimeTicks++;
            if (setTimeTicks >= SET_TIME) {
                world.setBlock(x, y, z, MonkeBlocks.smithingStone.id);
            }
        } else {
            setTimeTicks = 0;
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        setTimeTicks = nbt.getInt(SET_TIME_KEY);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt(SET_TIME_KEY, setTimeTicks);
    }
}
