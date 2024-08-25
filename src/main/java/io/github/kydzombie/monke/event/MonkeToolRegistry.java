package io.github.kydzombie.monke.event;

import io.github.kydzombie.monke.item.ToolPartItem;
import io.github.kydzombie.monke.item.tool.MonkeToolItem;

import java.util.ArrayList;
import java.util.HashMap;

public class MonkeToolRegistry {
    public static final HashMap<String, ToolPartItem> parts = new HashMap<>();
    public static final ArrayList<MonkeToolItem> tools = new ArrayList<>();
}
