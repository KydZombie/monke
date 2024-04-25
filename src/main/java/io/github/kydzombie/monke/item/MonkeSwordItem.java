package io.github.kydzombie.monke.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

public class MonkeSwordItem extends MonkeToolItem {
    public MonkeSwordItem(Identifier identifier, int damageBoost, TagKey<Block> effectiveBlocks, ToolPartItem[] parts) {
        super(identifier, damageBoost, effectiveBlocks, parts);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker);
        return true;
    }

    public boolean postMine(ItemStack stack, int blockId, int x, int y, int z, LivingEntity miner) {
        stack.damage(2, miner);
        return true;
    }

    public boolean isSuitableFor(Block block) {
        return super.isSuitableFor(block) || block.id == Block.COBWEB.id;
    }
}
