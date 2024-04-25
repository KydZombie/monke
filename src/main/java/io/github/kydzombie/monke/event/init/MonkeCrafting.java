package io.github.kydzombie.monke.event.init;

import io.github.kydzombie.monke.event.MonkeMaterialRegistry;
import io.github.kydzombie.monke.item.MonkeToolItem;
import io.github.kydzombie.monke.item.ToolPartItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;

public class MonkeCrafting {
    @EventListener
    private void registerRecipes(RecipeRegisterEvent event) {
        if (event.recipeId == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS.type()) {
            var woodenToolHandle = new ItemStack(MonkeItems.toolHandle);
            ToolPartItem.setMonkeMaterial(woodenToolHandle, MonkeMaterialRegistry.getMaterial("wood"));

            var hammerHead = new ItemStack(MonkeItems.hammerHead);
            ToolPartItem.setMonkeMaterial(hammerHead, MonkeMaterialRegistry.getMaterial("iron"));

            var hammer = new ItemStack(MonkeItems.hammer);
            MonkeToolItem.setMonkePart(hammer, woodenToolHandle);
            MonkeToolItem.setMonkePart(hammer, hammerHead);

            CraftingRegistry.addShapedRecipe(hammer, "III", "ISI", " S ", 'I', Item.IRON_INGOT, 'S', Item.STICK);

            var sawBlade = new ItemStack(MonkeItems.sawBlade);
            ToolPartItem.setMonkeMaterial(sawBlade, MonkeMaterialRegistry.getMaterial("iron"));

            var saw = new ItemStack(MonkeItems.saw);
            MonkeToolItem.setMonkePart(saw, woodenToolHandle);
            MonkeToolItem.setMonkePart(saw, sawBlade);

            CraftingRegistry.addShapedRecipe(saw, "SII", 'I', Item.IRON_INGOT, 'S', Item.STICK);
        }
    }
}
