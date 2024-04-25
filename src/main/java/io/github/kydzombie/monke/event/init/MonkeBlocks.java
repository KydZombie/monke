package io.github.kydzombie.monke.event.init;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.block.LooseSmithingStoneBlock;
import io.github.kydzombie.monke.block.SmithingAnvilBlock;
import io.github.kydzombie.monke.block.SmithingStoneBlock;
import io.github.kydzombie.monke.block.entity.LooseSmithingStoneBlockEntity;
import io.github.kydzombie.monke.block.entity.SmithingAnvilBlockEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;

public class MonkeBlocks {
    public static Block looseSmithingStone;
    public static Block smithingStone;

    public static Block smithingAnvil;

    @EventListener
    private void registerBlocks(BlockRegistryEvent event) {
        Monke.LOGGER.info("Registering blocks...");
        looseSmithingStone = new LooseSmithingStoneBlock(Monke.NAMESPACE.id("loose_smithing_stone"));
        smithingStone = new SmithingStoneBlock(Monke.NAMESPACE.id("smithing_stone"));

        smithingAnvil = new SmithingAnvilBlock(Monke.NAMESPACE.id("smithing_anvil"));
    }

    @EventListener
    private void registerBlockEntities(BlockEntityRegisterEvent event) {
        Monke.LOGGER.info("Registering block entities...");
        event.register(SmithingAnvilBlockEntity.class, Monke.NAMESPACE.id("smithing_anvil").toString());
        event.register(LooseSmithingStoneBlockEntity.class, Monke.NAMESPACE.id("loose_smithing_stone").toString());
    }
}
