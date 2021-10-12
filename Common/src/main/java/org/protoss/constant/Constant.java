package org.protoss.constant;

import org.protoss.utils.PropertyManager;

public class Constant {
    public static final int GAME_WIDTH = Integer.parseInt(PropertyManager.get("gameWidth"));
    public static final int GAME_HEIGHT = Integer.parseInt(PropertyManager.get("gameHeight"));
}
