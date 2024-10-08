package io.github.kydzombie.monke.mixin;

import io.github.kydzombie.monke.item.tool.MonkeSawItem;
import net.minecraft.block.Block;
import net.minecraft.class_564;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public class SawWarningMixin extends DrawContext {
    @Shadow
    private Minecraft minecraft;

    @Inject(at = @At("RETURN"), method = "render(FZII)V", locals = LocalCapture.CAPTURE_FAILHARD)
    private void addSawWarning(float bl, boolean i, int j, int par4, CallbackInfo ci, class_564 var5, int x, int y, TextRenderer textRenderer) {
        if (minecraft.player.method_1373()) return;
        var stack = minecraft.player.getHand();
        if (stack != null && stack.getItem() instanceof MonkeSawItem saw) {
            if (minecraft.player.handSwinging && minecraft.field_2823 != null && minecraft.field_2823.type == HitResultType.BLOCK) {
                var blockPos = new BlockPos(minecraft.field_2823.blockX, minecraft.field_2823.blockY, minecraft.field_2823.blockZ);
                var blockState = minecraft.world.getBlockState(blockPos);
                if (!blockState.isOf(Block.LOG)) return;
                var ableToMine = stack.getMaxDamage() - stack.getDamage() + 1;
                var blocksToMine = saw.findBlocks(minecraft.world, blockPos).length + 1;
                if (blocksToMine < ableToMine) return;
                GL11.glPushMatrix();
                GL11.glScalef(2f, 2f, 2f);
                drawCenteredTextWithShadow(minecraft.textRenderer, "Warning: Saw Durability Low!", x / 4, y, 0xFF2222);
                GL11.glScalef(.75f, .75f, .75f);
                drawCenteredTextWithShadow(minecraft.textRenderer, "Will only mine " + ableToMine + " / " + blocksToMine + " blocks!", x / 3, (int) ((y) / 4.5), 0xFF4444);
                GL11.glScalef(1.33f, 1.33f, 1.33f);
                if (blocksToMine == ableToMine) {
                    drawCenteredTextWithShadow(minecraft.textRenderer, "WILL BREAK", x / 4, y / 3, 0xFF4444);
                }

                GL11.glPopMatrix();
            }
        }
    }
}
