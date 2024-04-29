package io.github.kydzombie.monke.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

public class MonkeHoeItem extends MonkeToolItem {
    public MonkeHoeItem(Identifier identifier, int damageBoost, TagKey<Block> effectiveBlocks, ToolPartItem[] parts) {
        super(identifier, damageBoost, effectiveBlocks, parts);
    }

    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        int blockId = world.getBlockId(x, y, z);
        int aboveId = world.getBlockId(x, y + 1, z);
        if ((side == 0 || aboveId != 0 || blockId != Block.GRASS_BLOCK.id) && blockId != Block.DIRT.id) {
            return false;
        } else {
            Block var10 = Block.FARMLAND;
            world.playSound((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F, var10.soundGroup.getSound(), (var10.soundGroup.method_1976() + 1.0F) / 2.0F, var10.soundGroup.method_1977() * 0.8F);
            if (!world.isRemote) {
                world.setBlock(x, y, z, var10.id);
                stack.damage(1, user);
            }
            return true;
        }
    }
}
