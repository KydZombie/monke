package io.github.kydzombie.monke.event.init;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.block.SmithingAnvilBlock;
import io.github.kydzombie.monke.block.entity.SmithingAnvilBlockEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;

public class MonkeBlocks {
    public static Block smithingStone;

    public static Block smithingAnvil;

    @EventListener
    private void registerBlocks(BlockRegistryEvent event) {
        Monke.LOGGER.info("Registering blocks...");
        smithingStone = new TemplateBlock(Monke.NAMESPACE.id("smithing_stone"), Material.STONE)
                .setHardness(2.0f)
                .setResistance(12.0f)
                .setSoundGroup(Block.STONE_SOUND_GROUP)
                .setTranslationKey(Monke.NAMESPACE.id("smithing_stone"));

        smithingAnvil = new SmithingAnvilBlock(Monke.NAMESPACE.id("smithing_anvil"));
    }

    @EventListener
    private void registerBlockEntities(BlockEntityRegisterEvent event) {
        Monke.LOGGER.info("Registering block entities...");
        event.register(SmithingAnvilBlockEntity.class, Monke.NAMESPACE.id("smithing_anvil").toString());
    }
}
