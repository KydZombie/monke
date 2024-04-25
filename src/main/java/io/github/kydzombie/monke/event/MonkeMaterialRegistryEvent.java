package io.github.kydzombie.monke.event;

import io.github.kydzombie.monke.material.MonkeMaterial;
import net.mine_diver.unsafeevents.Event;

public class MonkeMaterialRegistryEvent extends Event {
    public void register(MonkeMaterial material) {
        MonkeMaterialRegistry.materials.put(material.name(), material);
    }
}
