package org.protoss.constant;

import org.protoss.utils.PropertyManager;

import java.util.Objects;

public class Constant {
    public static final int GAME_WIDTH = Integer.parseInt(Objects.requireNonNull(PropertyManager.get("gameWidth")));
    public static final int GAME_HEIGHT = Integer.parseInt(Objects.requireNonNull(PropertyManager.get("gameHeight")));
    public static final String SERVER_HOST = PropertyManager.get("ServerHost");
    public static final int SERVER_PORT = Integer.parseInt(Objects.requireNonNull(PropertyManager.get("ServerPort")));
}
