package org.protoss.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {

    private static final ExecutorService singlePool = Executors.newSingleThreadExecutor();

    private ThreadUtil() {
    }

    public static ExecutorService getSingleThreadPool() {
        return singlePool;
    }
}
