package io.github.kydzombie.monke.custom.material;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResultType;
import net.modificationstation.stationapi.api.block.BlockState;

import javax.annotation.Nullable;

public class EnergizedWoodMaterial extends MonkeMaterial {
    private static final String HEAL_TICK_KEY = "energizing_wood_heal_tick";
    private static final String MINING_VELOCITY_KEY = "energizing_wood_velocity";
    private static final String MINING_GRACE_PERIOD_KEY = "energizing_wood_grace_period";
    private static final int REPAIR_TIME_IN_HAND = 4 * 20;
    private static final int REPAIR_TIME_IN_INVENTORY = 8 * 20;
    private static final int REPAIR_TIME_IN_STORAGE = 10 * 20;
    private static final float MINING_SPEED_INCREASE_PER_BLOCK = 0.1f;
    private static final int MINING_GRACE_TIME = (int) (0.5 * 20);

    public EnergizedWoodMaterial(String name, ToolMaterial toolMaterial, int color, CreationMethod creationMethod, ItemStack materialItem, float percentDurabilityBoost) {
        super(name, toolMaterial, color, creationMethod, materialItem, percentDurabilityBoost);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state, NbtCompound partNbt, int partIndex) {
        if (partIndex == 0) return 1f;
        return 1f + (float) Math.sqrt(MINING_SPEED_INCREASE_PER_BLOCK * stack.getStationNbt().getCompound("monke_data").getInt(MINING_VELOCITY_KEY));
    }

    @Override
    public void postMine(ItemStack stack, int blockId, int x, int y, int z, LivingEntity miner, int partIndex) {
        if (partIndex != 0 && miner instanceof PlayerEntity) {
            var modifierNbt = stack.getStationNbt().getCompound("monke_data");
            modifierNbt.putInt(MINING_VELOCITY_KEY, modifierNbt.getInt(MINING_VELOCITY_KEY) + 1);
            stack.getStationNbt().put("monke_data", modifierNbt);
        }
        super.postMine(stack, blockId, x, y, z, miner, partIndex);
    }

    @Override
    public void inventoryTick(@Nullable Entity holder, ItemStack stack, NbtCompound partNbt, int partIndex, boolean held) {
        var monkeNbt = stack.getStationNbt().getCompound("monke_data");
        if (partIndex == 0) {
            if (stack.getDamage() <= 0) {
                monkeNbt.putInt(HEAL_TICK_KEY, 0);
                stack.getStationNbt().put("monke_data", monkeNbt);
                return;
            }

            var i = monkeNbt.getInt(HEAL_TICK_KEY);
            if (held) {
                if (i >= REPAIR_TIME_IN_HAND) {
                    monkeNbt.putInt(HEAL_TICK_KEY, 0);
                    stack.setDamage(stack.getDamage() - 1);
                    stack.getStationNbt().put("monke_data", monkeNbt);
                    return;
                }
            } else if (holder != null) {
                if (i >= REPAIR_TIME_IN_INVENTORY) {
                    monkeNbt.putInt(HEAL_TICK_KEY, 0);
                    stack.setDamage(stack.getDamage() - 1);
                    stack.getStationNbt().put("monke_data", monkeNbt);
                    return;
                }
            } else if (i >= REPAIR_TIME_IN_STORAGE) {
                monkeNbt.putInt(HEAL_TICK_KEY, 0);
                stack.setDamage(stack.getDamage() - 1);
                stack.getStationNbt().put("monke_data", monkeNbt);
                return;
            }
            monkeNbt.putInt(HEAL_TICK_KEY, i + 1);
            stack.getStationNbt().put("monke_data", monkeNbt);
        } else if (holder instanceof PlayerEntity player) {
            var minecraft = ((Minecraft) FabricLoader.getInstance().getGameInstance());
            if (!held || !player.handSwinging || minecraft.field_2823 == null || minecraft.field_2823.type != HitResultType.BLOCK) {
                if (monkeNbt.getInt(MINING_GRACE_PERIOD_KEY) >= MINING_GRACE_TIME) {
                    monkeNbt.putInt(MINING_VELOCITY_KEY, 0);
                } else {
                    monkeNbt.putInt(MINING_GRACE_PERIOD_KEY, monkeNbt.getInt(MINING_GRACE_PERIOD_KEY) + 1);
                }

                stack.getStationNbt().put("monke_data", monkeNbt);
                return;
            }

            monkeNbt.putInt(MINING_GRACE_PERIOD_KEY, 0);

            stack.getStationNbt().put("monke_data", monkeNbt);
        }
    }
}
