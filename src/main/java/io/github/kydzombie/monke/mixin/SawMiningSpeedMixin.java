package io.github.kydzombie.monke.mixin;

import io.github.kydzombie.monke.item.MonkeSawItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.block.AbstractBlockState;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlockState.class)
public abstract class SawMiningSpeedMixin {
    @Shadow
    public abstract Block getBlock();

    @Shadow
    protected abstract BlockState asBlockState();

    @Inject(at = @At("HEAD"), method = "calcBlockBreakingDelta(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F", cancellable = true)
    private void inject(PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (!player.method_1373() && player.getHand() != null && player.getHand().getItem() instanceof MonkeSawItem saw) {
            var blockState = asBlockState();
            if (!blockState.isIn(saw.getEffectiveBlocks(player.getHand()))) return;
            var delta = getBlock().calcBlockBreakingDelta(blockState, player, world, pos);
            var blocks = saw.findBlocks(player.world, pos);
            if (blocks.length >= 2) {
                delta /= blocks.length / 1.5f;
                cir.setReturnValue(delta);
            }
        }
    }
}
