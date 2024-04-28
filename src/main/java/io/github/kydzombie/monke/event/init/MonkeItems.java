package io.github.kydzombie.monke.event.init;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.compat.MonkeHMICompat;
import io.github.kydzombie.monke.item.*;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.item.ItemMiningSpeedMultiplierOnStateEvent;
import net.modificationstation.stationapi.api.event.registry.AfterBlockAndItemRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class MonkeItems {
    /* Items */

    public static Item energizedWoodShard;

    /* Parts */

    // Generic Parts
    public static ToolPartItem toolHandle;

    // Unique Parts
    public static ToolPartItem pickaxeHead;
    public static ToolPartItem axeHead;
    public static ToolPartItem shovelHead;
    public static ToolPartItem hoeHead;
    public static ToolPartItem swordBlade;
    public static ToolPartItem swordGuard;

    public static ToolPartItem hammerHead;
    public static ToolPartItem sawBlade;

    /* Tools */

    // Vanilla Tools
    public static MonkeToolItem pickaxe;
    public static MonkeToolItem axe;
    public static MonkeToolItem shovel;
    public static MonkeToolItem hoe;
    public static MonkeToolItem sword;

    // Extended Tools
    public static MonkeToolItem hammer;
    public static MonkeToolItem saw;

    @EventListener
    private void registerItems(ItemRegistryEvent event) {
        Monke.LOGGER.info("Registering items...");
        energizedWoodShard = new TemplateItem(Monke.NAMESPACE.id("energized_wood_shard")).setTranslationKey(Monke.NAMESPACE.id("energized_wood_shard"));

        Monke.LOGGER.info("Registering tool parts...");
        toolHandle = new ToolPartItem(Monke.NAMESPACE.id("tool_handle"));

        pickaxeHead = new ToolPartItem(Monke.NAMESPACE.id("pickaxe_head"));
        axeHead = new ToolPartItem(Monke.NAMESPACE.id("axe_head"));
        shovelHead = new ToolPartItem(Monke.NAMESPACE.id("shovel_head"));
        hoeHead = new ToolPartItem(Monke.NAMESPACE.id("hoe_head"));
        swordBlade = new ToolPartItem(Monke.NAMESPACE.id("sword_blade"));
        swordGuard = new ToolPartItem(Monke.NAMESPACE.id("sword_guard"));

        hammerHead = new ToolPartItem(Monke.NAMESPACE.id("hammer_head"));
        sawBlade = new ToolPartItem(Monke.NAMESPACE.id("saw_blade"));

        Monke.LOGGER.info("Registering tools...");
        // Vanilla Tools
        pickaxe = new MonkeToolItem(
                Monke.NAMESPACE.id("pickaxe"),
                2,
                TagKey.of(BlockRegistry.INSTANCE.getKey(), Identifier.of("minecraft:mineable/pickaxe")),
                new ToolPartItem[]{ pickaxeHead, toolHandle }
        );
        axe = new MonkeToolItem(
                Monke.NAMESPACE.id("axe"),
                3,
                TagKey.of(BlockRegistry.INSTANCE.getKey(), Identifier.of("minecraft:mineable/axe")),
                new ToolPartItem[]{ axeHead, toolHandle }
        );
        shovel = new MonkeToolItem(
                Monke.NAMESPACE.id("shovel"),
                1,
                TagKey.of(BlockRegistry.INSTANCE.getKey(), Identifier.of("minecraft:mineable/shovel")),
                new ToolPartItem[]{ shovelHead, toolHandle }
        );
        hoe = new MonkeHoeItem(
                Monke.NAMESPACE.id("hoe"),
                1,
                TagKey.of(BlockRegistry.INSTANCE.getKey(), Identifier.of("minecraft:mineable/hoe")),
                new ToolPartItem[]{ hoeHead, toolHandle }
        );
        sword = new MonkeSwordItem(
                Monke.NAMESPACE.id("sword"),
                4,
                TagKey.of(BlockRegistry.INSTANCE.getKey(), Identifier.of("minecraft:mineable/sword")),
                new ToolPartItem[]{ swordBlade, swordGuard, toolHandle }
        );

        // Extended Tools
        hammer = new MonkeHammerItem(
                Monke.NAMESPACE.id("hammer"),
                3,
                TagKey.of(BlockRegistry.INSTANCE.getKey(), Identifier.of("minecraft:mineable/pickaxe")),
                new ToolPartItem[]{ hammerHead, toolHandle }
        );
        saw = new MonkeSawItem(
                Monke.NAMESPACE.id("saw"),
                3,
                TagKey.of(BlockRegistry.INSTANCE.getKey(), Identifier.of("minecraft:mineable/axe")),
                new ToolPartItem[]{sawBlade, toolHandle }
        );
    }

    @EventListener
    private void miningSpeed(ItemMiningSpeedMultiplierOnStateEvent event) {
        // TODO: Wait for this to get fixed in StationAPI?
        if (event.itemStack != null && event.itemStack.getItem() instanceof MonkeToolItem tool) {
            var miningSpeed = event.miningSpeedMultiplier;
            var nbt = event.itemStack.getStationNbt();
            var partsNbt = nbt.getCompound("monke_parts");
            for (int i = 0; i < tool.parts.length; i++) {
                var part = tool.parts[i];
                var partNbt = partsNbt.getCompound(part.getTranslationKey());
                var material = MonkeToolItem.getPartMaterialFromNbt(partNbt);
                if (material == null) continue;
                miningSpeed *= material.getMiningSpeedMultiplier(event.itemStack, event.state, partNbt, i);
            }
            event.miningSpeedMultiplier = miningSpeed;
        }
    }

    @EventListener
    private void afterItems(AfterBlockAndItemRegisterEvent event) {
        if (FabricLoader.getInstance().isModLoaded("hmifabric")) {
            MonkeHMICompat.addItems(event);
        }
    }
}
