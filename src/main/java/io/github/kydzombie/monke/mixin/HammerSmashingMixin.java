package io.github.kydzombie.monke.mixin;

import io.github.kydzombie.monke.item.MonkeHammerItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class HammerSmashingMixin {
    @Shadow protected abstract void dropStack(World world, int x, int y, int z, ItemStack itemStack);

    @Inject(
            method = "afterBreak(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;IIII)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;dropStacks(Lnet/minecraft/world/World;IIII)V"),
            cancellable = true
    )
    private void replaceDropId(World world, PlayerEntity playerEntity, int x, int y, int z, int meta, CallbackInfo ci) {
        if (!world.isRemote && playerEntity.getHand() != null && playerEntity.getHand().getItem() instanceof MonkeHammerItem hammer) {
            var drop = hammer.getHammeringProduct(playerEntity.getHand(), (Block) (Object) this, world, playerEntity, x, y, z, meta);
            if (drop != null) {
                dropStack(world, x, y, z, drop);
                ci.cancel();
            }
        }
    }
}
