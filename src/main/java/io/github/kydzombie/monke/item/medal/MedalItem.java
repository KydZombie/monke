package io.github.kydzombie.monke.item.medal;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class MedalItem extends TemplateItem implements Accessory {
    private static final String[] ACCESSORY_TYPES = new String[]{"misc"};

    public MedalItem(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack item) {
        return ACCESSORY_TYPES;
    }
}
