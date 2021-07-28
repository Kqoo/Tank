package org.protoss.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

    private static Properties INSTANCE = new Properties();

    private PropertyManager() {
    }

    static {
        try {
            INSTANCE.load(PropertyManager.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getInstance(){
        return INSTANCE;
    }

    public static String get(String key) {
        if (INSTANCE == null) {
            return null;
        }
        return (String) INSTANCE.get(key);
    }
}
