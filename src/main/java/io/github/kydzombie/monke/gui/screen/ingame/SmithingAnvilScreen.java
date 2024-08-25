package io.github.kydzombie.monke.gui.screen.ingame;

import io.github.kydzombie.monke.block.entity.SmithingAnvilBlockEntity;
import io.github.kydzombie.monke.event.init.MonkeItems;
import io.github.kydzombie.monke.gui.screen.SmithingAnvilScreenHandler;
import io.github.kydzombie.monke.gui.widget.ToolPartButtonWidget;
import io.github.kydzombie.monke.packet.ChooseSmithingOutputPacket;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.lwjgl.opengl.GL11;

public class SmithingAnvilScreen extends HandledScreen {
    final SmithingAnvilBlockEntity smithingAnvilBlockEntity;

    public SmithingAnvilScreen(PlayerInventory inventory, SmithingAnvilBlockEntity smithingAnvilBlockEntity) {
        super(new SmithingAnvilScreenHandler(inventory, smithingAnvilBlockEntity));
        this.smithingAnvilBlockEntity = smithingAnvilBlockEntity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        super.init();
        int xOffset = (width - backgroundWidth) / 2;
        int yOffset = (height - backgroundHeight) / 2;
        buttons.add(new ToolPartButtonWidget(0, xOffset - 2, yOffset + 52, MonkeItems.pickaxeHead));
        buttons.add(new ToolPartButtonWidget(1, xOffset + 18, yOffset + 52, MonkeItems.axeHead));
        buttons.add(new ToolPartButtonWidget(2, xOffset + 38, yOffset + 52, MonkeItems.shovelHead));
        buttons.add(new ToolPartButtonWidget(3, xOffset + 58, yOffset + 52, MonkeItems.hoeHead));
        buttons.add(new ToolPartButtonWidget(4, xOffset + 78, yOffset + 52, MonkeItems.swordBlade));
        buttons.add(new ToolPartButtonWidget(5, xOffset + 98, yOffset + 52, MonkeItems.swordGuard));
        buttons.add(new ToolPartButtonWidget(6, xOffset + 118, yOffset + 52, MonkeItems.toolHandle));
        buttons.add(new ToolPartButtonWidget(7, xOffset + 138, yOffset + 52, MonkeItems.hammerHead));
        buttons.add(new ToolPartButtonWidget(8, xOffset + 158, yOffset + 52, MonkeItems.sawBlade));

        smithingAnvilBlockEntity.setSelectedPart(null);

//        for (var obj : buttons) {
//            if (obj instanceof ToolPartButtonWidget button) {
//                button.active = smithingAnvilBlockEntity.getSelectedPart() != button.part;
//            }
//        }
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if (button instanceof ToolPartButtonWidget partButton) {
            smithingAnvilBlockEntity.setSelectedPart(partButton.part);
            for (var otherButton : buttons) {
                if (otherButton instanceof ToolPartButtonWidget otherPartButton) {
                    otherPartButton.active = partButton != otherPartButton;
                    if (partButton == otherPartButton) {
                        PacketHelper.send(new ChooseSmithingOutputPacket(partButton.partName));
                    }
                }
            }
        }
        super.buttonClicked(button);
    }

    protected void drawForeground() {
        textRenderer.draw("Smithing Anvil", 60, 6, 4210752);
        textRenderer.draw("Inventory", 8, backgroundHeight - 96 + 2, 4210752);
    }

    protected void drawBackground(float tickDelta) {
        int var2 = minecraft.textureManager.getTextureId("/assets/monke/gui/smithing_anvil_gui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(var2);
        int var3 = (width - backgroundWidth) / 2;
        int var4 = (height - backgroundHeight) / 2;
        drawTexture(var3, var4, 0, 0, backgroundWidth, backgroundHeight);
    }
}
