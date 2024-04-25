package io.github.kydzombie.monke.gui.widget;

import cyclops.data.tuple.Tuple2;
import io.github.kydzombie.monke.event.init.MonkeItems;
import io.github.kydzombie.monke.item.ToolPartItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

public class ToolPartButtonWidget extends ButtonWidget {
    public final ToolPartItem part;

    public ToolPartButtonWidget(int id, int x, int y, ToolPartItem part) {
        super(id, x, y, 20, 20, part.getTranslatedName());
        this.part = part;
    }

    private static final HashMap<ToolPartItem, Tuple2<Integer, Integer>> TOOL_PART_COORDS = new HashMap<>() {{
        put(MonkeItems.pickaxeHead, new Tuple2<>(0, 166));
        put(MonkeItems.axeHead, new Tuple2<>(16, 166));
        put(MonkeItems.shovelHead, new Tuple2<>(32, 166));
        put(MonkeItems.hoeHead, new Tuple2<>(48, 166));
        put(MonkeItems.swordBlade, new Tuple2<>(64, 166));
        put(MonkeItems.swordGuard, new Tuple2<>(80, 166));
        put(MonkeItems.toolHandle, new Tuple2<>(96, 166));
        put(MonkeItems.hammerHead, new Tuple2<>(112, 166));
        put(MonkeItems.sawBlade, new Tuple2<>(128, 166));
    }};

    @Override
    public void render(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.visible) {
            GL11.glBindTexture(3553, minecraft.textureManager.getTextureId("/gui/gui.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean hovering = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            int vOffset = method_1187(hovering);
            drawTexture(x, y, 0, 46 + vOffset * 20, width / 2, height);
            drawTexture(x + width / 2, y, 200 - width / 2, 46 + vOffset * 20, width / 2, height);
            this.method_1188(minecraft, mouseX, mouseY);

            GL11.glBindTexture(3553, minecraft.textureManager.getTextureId("/assets/monke/gui/smithing_anvil_gui.png"));

            var coords = TOOL_PART_COORDS.get(part);
            drawTexture(x + 2, y + 2, coords._1(), coords._2(), 16, 16);
        }
    }
}
