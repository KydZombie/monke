package io.github.kydzombie.monke.item;

import io.github.kydzombie.monke.custom.material.MonkeMaterial;
import io.github.kydzombie.monke.event.MonkeMaterialRegistry;
import io.github.kydzombie.monke.event.MonkeToolRegistry;
import io.github.kydzombie.monke.event.ingame.MonkePlayerHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.impl.entity.player.PlayerAPI;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

public class ToolPartItem extends TemplateItem implements CustomTooltipProvider {
    public ToolPartItem(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier);
        MonkeToolRegistry.parts.put(identifier.toString(), this);
    }

    public static @Nullable MonkeMaterial getMonkeMaterial(ItemStack stack) {
        var nbt = stack.getStationNbt();
        var compound = nbt.getCompound("monke_data");
        return MonkeMaterialRegistry.getMaterial(compound.getString("material"));
    }

    public static void setMonkeMaterial(ItemStack stack, MonkeMaterial material) {
        var nbt = stack.getStationNbt();
        var compound = nbt.getCompound("monke_data");
        compound.putString("material", material.name);
        nbt.put("monke_data", compound);
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        var tooltip = new ArrayList<String>();
        var material = getMonkeMaterial(stack);
        tooltip.add(originalTooltip + ": " + (material != null ? material.name : "none"));
        var nbt = stack.getStationNbt();
        var monkeData = nbt.getCompound("monke_data");
        var buffs = monkeData.getList("buffs");
        for (int i = 0; i < buffs.size(); i++) {
            var buffNbt = (NbtCompound) buffs.get(i);
            if (Objects.equals(buffNbt.getString("type"), "mining_speed")) {
                tooltip.add("Mining Speed: " + buffNbt.getFloat("speed"));
            }
        }
        return tooltip.toArray(String[]::new);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        super.onCraft(stack, world, player);

        var monkeHandler = (MonkePlayerHandler) PlayerAPI.getPlayerHandler(player, MonkePlayerHandler.class);
        monkeHandler.addCraft();
    }
}
