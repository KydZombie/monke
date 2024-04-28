package io.github.kydzombie.monke.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

import javax.annotation.Nullable;

public class MonkeHammerItem extends MonkeToolItem {
    public MonkeHammerItem(Identifier identifier, int damageBoost, TagKey<Block> effectiveBlocks, ToolPartItem[] parts) {
        super(identifier, damageBoost, effectiveBlocks, parts);
    }

    @Override
    public boolean postMine(ItemStack stack, int blockId, int x, int y, int z, LivingEntity miner) {
        if (blockId == Block.STONE.id || blockId == Block.COBBLESTONE.id || blockId == Block.GRAVEL.id) {
            stack.damage(1, miner);
        }
        return super.postMine(stack, blockId, x, y, z, miner);
    }

    public @Nullable ItemStack getHammeringProduct(ItemStack stack, Block block, World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        if (block == Block.STONE || block == Block.COBBLESTONE) {
            return new ItemStack(Block.GRAVEL);
        } else if (block == Block.GRAVEL) {
            return new ItemStack(Block.SAND);
        } else {
            return null;
        }
    }
}
