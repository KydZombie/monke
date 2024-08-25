package io.github.kydzombie.monke.item.tool;

import io.github.kydzombie.monke.item.ToolPartItem;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;

public class MonkeSawItem extends MonkeToolItem {
    private static final int VEIN_MINE_CAP = 50;

    public MonkeSawItem(Identifier identifier, int damageBoost, TagKey<Block> effectiveBlocks, ToolPartItem[] parts) {
        super(identifier, damageBoost, effectiveBlocks, parts);
    }

    @Override
    public boolean postMine(ItemStack stack, int blockId, int x, int y, int z, LivingEntity miner) {
        if (!miner.method_1373() && blockId == Block.LOG.id) {
            veinMine(stack, blockId, x, y, z, miner);
        }
        return super.postMine(stack, blockId, x, y, z, miner);
    }

    private ArrayList<Vec3i> checkNearbyBlocks(World world, int blockId, Vec3i pos) {
        ArrayList<Vec3i> foundBlocks = new ArrayList<>();
        for (int x = pos.x - 1; x <= pos.x + 1; x++) {
            for (int y = pos.y; y <= pos.y + 1; y++) {
                for (int z = pos.z - 1; z <= pos.z + 1; z++) {
                    if (x == pos.x && y == pos.y && z == pos.z) continue;
                    if (world.getBlockId(x, y, z) == blockId) {
                        foundBlocks.add(new Vec3i(x, y, z));
                    }
                }
            }
        }

        return foundBlocks;
    }

    public Vec3i[] findBlocks(World world, BlockPos pos) {
        return findBlocks(world, world.getBlockId(pos.x, pos.y, pos.z), pos.x, pos.y, pos.z);
    }

    public Vec3i[] findBlocks(World world, int blockId, int x, int y, int z) {
        ArrayList<Vec3i> blocksToCheck = new ArrayList<>();
        ArrayList<Vec3i> blocksChecked = new ArrayList<>();
        blocksToCheck.add(new Vec3i(x, y, z));

        ArrayList<Vec3i> check;
        while (!blocksToCheck.isEmpty() && blocksChecked.size() < VEIN_MINE_CAP) {

            check = checkNearbyBlocks(world, blockId, blocksToCheck.get(0));

            blocksChecked.add(blocksToCheck.get(0));
            blocksToCheck.removeAll(blocksChecked);
            check.removeAll(blocksChecked);

            blocksToCheck.addAll(check);
        }

        int size = blocksChecked.size();

        if (size > VEIN_MINE_CAP) {
            blocksChecked.subList(VEIN_MINE_CAP, size).clear();
        }

        blocksChecked.remove(0);

        return blocksChecked.toArray(Vec3i[]::new);
    }

    private void veinMine(ItemStack stack, int blockId, int x, int y, int z, LivingEntity miner) {
        var world = miner.world;

        for (var pos : findBlocks(world, blockId, x, y, z)) {
            if (stack.getDamage() >= stack.getMaxDamage()) return;

            var foundId = world.getBlockId(pos.x, pos.y, pos.z);
            if (foundId != blockId) continue;
            Block block = Block.BLOCKS[foundId];
            int meta = world.getBlockMeta(pos.x, pos.y, pos.z);
            world.setBlock(pos.x, pos.y, pos.z, 0);
            if (miner instanceof PlayerEntity player) {
                block.afterBreak(world, player, pos.x, pos.y, pos.z, meta);
            } else {
                block.dropStacks(world, pos.x, pos.y, pos.z, meta);
            }

            stack.damage(1, miner);
        }
    }
}
