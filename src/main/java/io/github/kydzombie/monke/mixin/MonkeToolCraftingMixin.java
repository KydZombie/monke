package io.github.kydzombie.monke.mixin;

import io.github.kydzombie.monke.event.MonkeToolRegistry;
import io.github.kydzombie.monke.item.ToolPartItem;
import io.github.kydzombie.monke.item.tool.MonkeToolItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(CraftingRecipeManager.class)
public class MonkeToolCraftingMixin {
    @Inject(at = @At("HEAD"), method = "craft(Lnet/minecraft/inventory/CraftingInventory;)Lnet/minecraft/item/ItemStack;", cancellable = true)
    private void injectMonkeCraft(CraftingInventory craftingInventory, CallbackInfoReturnable<ItemStack> cir) {
        ArrayList<ItemStack> usedItems = new ArrayList<>();
        for (int i = 0; i < craftingInventory.size(); i++) {
            var stack = craftingInventory.getStack(i);
            if (stack != null) {
                if (!(stack.getItem() instanceof ToolPartItem)) return;
                usedItems.add(stack);
            }
        }
        for (var tool : MonkeToolRegistry.tools) {
            var outputTool = new ItemStack(tool);
            if (tool.parts.length != usedItems.size()) continue;
            var foundAllParts = true;
            for (var toolPart : tool.parts) {
                var foundThisPart = false;
                for (var usedItem : usedItems) {
                    if (toolPart != usedItem.getItem()) continue;
                    MonkeToolItem.setMonkePart(outputTool, usedItem);
                    foundThisPart = true;
                }
                if (!foundThisPart) {
                    foundAllParts = false;
                    break;
                }
            }
            if (!foundAllParts) continue;
            cir.setReturnValue(outputTool);
        }
    }
}
