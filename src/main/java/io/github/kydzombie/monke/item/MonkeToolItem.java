package io.github.kydzombie.monke.item;

import io.github.kydzombie.monke.event.MonkeMaterialRegistry;
import io.github.kydzombie.monke.event.MonkeToolRegistry;
import io.github.kydzombie.monke.material.MonkeMaterial;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.item.tool.ToolMaterialFactory;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.template.item.TemplateToolItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class MonkeToolItem extends TemplateToolItem implements CustomTooltipProvider {
    private static final ToolMaterial MISSING_MATERIAL =
            ToolMaterialFactory.create("monke:missing_tool_material", 0, 0, 0, 0);

    public final ToolPartItem[] parts;

    public MonkeToolItem(Identifier identifier, int damageBoost, TagKey<Block> effectiveBlocks, ToolPartItem[] parts) {
        super(identifier, damageBoost, MISSING_MATERIAL, new Block[]{});
        this.parts = parts;
        setEffectiveBlocks(effectiveBlocks);
        setTranslationKey(identifier);
        MonkeToolRegistry.tools.add(this);
    }

    public static @Nullable MonkeMaterial getMonkeMaterial(ItemStack stack, ToolPartItem part) {
        var nbt = stack.getStationNbt();
        var partsNbt = nbt.getCompound("monke_parts");
        var partNbt = partsNbt.getCompound(part.getTranslationKey());
        return MonkeMaterialRegistry.getMaterial(partNbt.getString("material"));
    }

    public static void setMonkePart(@NotNull ItemStack stack, @NotNull ItemStack part) {
        var nbt = stack.getStationNbt();
        var partsNbt = nbt.getCompound("monke_parts");
        partsNbt.put(part.getTranslationKey(), part.getStationNbt().getCompound("monke_data").copy());
        nbt.put("monke_parts", partsNbt);
    }

    @Override
    public ToolMaterial getMaterial(ItemStack stack) {
        var primaryMaterial = getMonkeMaterial(stack, parts[0]);
        return primaryMaterial != null ? primaryMaterial.toolMaterial() : MISSING_MATERIAL;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getMaterial(stack).getDurability();
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        ArrayList<String> tooltip = new ArrayList<>();
        tooltip.add(originalTooltip);
        for (var part : parts) {
            var material = getMonkeMaterial(stack, part);
            tooltip.add(part.getTranslatedName() + ": " + (material != null ? material.name() : "none"));
        }
        return tooltip.toArray(String[]::new);
    }
}
