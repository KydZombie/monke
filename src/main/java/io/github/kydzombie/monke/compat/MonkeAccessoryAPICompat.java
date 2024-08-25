package io.github.kydzombie.monke.compat;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;

public class MonkeAccessoryAPICompat {
    @EventListener
    private void registerSlots(InitEvent event) {
        AccessoryRegister.add("misc");
        AccessoryRegister.add("misc");
    }
}
