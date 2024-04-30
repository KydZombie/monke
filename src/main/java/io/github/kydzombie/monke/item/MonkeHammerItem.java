package io.github.kydzombie.monke.item;

import io.github.kydzombie.monke.event.init.MonkeItems;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
        if (blockId == Block.STONE.id || blockId == Block.COBBLESTONE.id ||
                blockId == Block.GRAVEL.id ||
                blockId == Block.IRON_ORE.id || blockId == Block.GOLD_ORE.id) {
            stack.damage(1, miner);
        }
        return super.postMine(stack, blockId, x, y, z, miner);
    }

    public @Nullable ItemStack getHammeringProduct(ItemStack stack, Block block, World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        if (block == Block.STONE || block == Block.COBBLESTONE) {
            return new ItemStack(Block.GRAVEL);
        } else if (block == Block.GRAVEL) {
            return new ItemStack(Block.SAND);
        } else if (block == Block.IRON_ORE) {
            // TODO: Make it drop crushed ore or similar
            return new ItemStack(MonkeItems.crushedIron, random.nextInt(0, 4) == 3 ? 2 : 1);
        } else if (block == Block.GOLD_ORE) {
            return new ItemStack(MonkeItems.crushedGold, random.nextInt(0, 4) == 3 ? 2 : 1);
        } else {
            return null;
        }
    }
}
