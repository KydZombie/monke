package io.github.kydzombie.monke.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class SmithingStoneBlock extends TemplateBlock {
    public SmithingStoneBlock(Identifier identifier) {
        super(identifier, Material.STONE);
        setTranslationKey(identifier);
        setHardness(2.0f);
        setResistance(12.0f);
        setSoundGroup(Block.STONE_SOUND_GROUP);
    }
}
