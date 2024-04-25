package io.github.kydzombie.monke.block;

import io.github.kydzombie.monke.block.entity.LooseSmithingStoneBlockEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

// TODO Make a falling block
public class LooseSmithingStoneBlock extends TemplateBlockWithEntity {
    public LooseSmithingStoneBlock(Identifier identifier) {
        super(identifier, Material.SAND);
        setTranslationKey(identifier);
        setHardness(1.0F);
        setSoundGroup(GRAVEL_SOUND_GROUP);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new LooseSmithingStoneBlockEntity();
    }
}
