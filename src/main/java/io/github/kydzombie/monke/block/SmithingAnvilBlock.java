package io.github.kydzombie.monke.block;

import io.github.kydzombie.monke.Monke;
import io.github.kydzombie.monke.block.entity.SmithingAnvilBlockEntity;
import io.github.kydzombie.monke.gui.screen.SmithingAnvilScreenHandler;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class SmithingAnvilBlock extends TemplateBlockWithEntity {
    private static final Random random = new Random();

    public SmithingAnvilBlock(Identifier identifier) {
        super(identifier, Material.METAL);
        setTranslationKey(identifier);
        setHardness(5.0f);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new SmithingAnvilBlockEntity();
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (!player.method_1373()) {
            var blockEntity = (SmithingAnvilBlockEntity) world.getBlockEntity(x, y, z);
            GuiHelper.openGUI(
                    player,
                    Monke.NAMESPACE.id("smithing_anvil"),
                    blockEntity,
                    new SmithingAnvilScreenHandler(player.inventory, blockEntity)
            );
            return true;
        }
        return super.onUse(world, x, y, z, player);
    }

    @Override
    public void onBreak(World world, int x, int y, int z) {
        var smithingAnvilBlockEntity = (SmithingAnvilBlockEntity) world.getBlockEntity(x, y, z);

        for (int slot = 0; slot < smithingAnvilBlockEntity.size() - 1; ++slot) {
            ItemStack itemStack = smithingAnvilBlockEntity.getStack(slot);
            if (itemStack != null) {
                float xOffset = random.nextFloat() * 0.8F + 0.1F;
                float yOffset = random.nextFloat() * 0.8F + 0.1F;
                float zOffset = random.nextFloat() * 0.8F + 0.1F;

                while (itemStack.count > 0) {
                    int dropCount = random.nextInt(21) + 10;
                    if (dropCount > itemStack.count) {
                        dropCount = itemStack.count;
                    }

                    itemStack.count -= dropCount;
                    ItemStack toDrop = itemStack.copy();
                    toDrop.count = dropCount;
                    ItemEntity itemEntity = new ItemEntity(world, x + xOffset, y + yOffset, z + zOffset, toDrop);
                    float intensity = 0.05F;
                    itemEntity.velocityX = random.nextGaussian() * intensity;
                    itemEntity.velocityY = random.nextGaussian() * intensity + 0.2F;
                    itemEntity.velocityZ = random.nextGaussian() * intensity;
                    world.method_210(itemEntity);
                }
            }
        }

        super.onBreak(world, x, y, z);
    }
}
