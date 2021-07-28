package org.protoss.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    static Properties properties = new Properties();

    static {
        try {
            properties.load(PropertyManager.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }
}
