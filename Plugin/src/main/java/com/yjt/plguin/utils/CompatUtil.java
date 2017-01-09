package com.yjt.plguin.utils;

import com.yjt.plguin.BuildConfig;
import com.yjt.plguin.constant.Constant;
import com.yjt.plguin.exception.PluginLoadException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CompatUtil {

    private static CompatUtil mInstance;
    private final Map<String, Integer> mHostLibraries;

    private CompatUtil() {
        // cannot be instantiated
        mHostLibraries = new HashMap<>();
        registerLibrary(BuildConfig.NAME, BuildConfig.VERSION_CODE); // Frontia Version.
    }

    public static synchronized CompatUtil getInstance() {
        if (mInstance == null) {
            mInstance = new CompatUtil();
        }
        return mInstance;
    }

    public static synchronized void releaseInstance() {
        if (mInstance != null) {
            mInstance = null;
        }
    }

    public void registerLibrary(String name, int version) {
        if (mHostLibraries.containsKey(name)) {
            throw new RuntimeException("Library duplicated.");
        }
        mHostLibraries.put(name, version);
    }

    public void checkCompat(Map<String, Integer> dependencies, Set<String> ignores) throws PluginLoadException {
        if (dependencies != null && dependencies.size() > 0) {
            Set<String> keySet = dependencies.keySet();
            StringBuilder builder = null;
            for (String key : keySet) {
                int required = dependencies.get(key);
                int current = mHostLibraries.containsKey(key) ? mHostLibraries.get(key) : Constant.Manifest.DEFAULT_VALUE;
                if (current < required && (ignores == null || !ignores.contains(key))) {
                    if (builder == null) {
                        builder = new StringBuilder();
                    }
                    builder.append("Library not satisfied, name = ")
                            .append(key)
                            .append(", current = ")
                            .append(current)
                            .append(", required = ")
                            .append(required).append("\n");
                }
            }
            if (builder != null) {
                throw new PluginLoadException(builder.toString(), Constant.LoadException.PLUGIN_DEPENDENCY_FAILED);
            }
        }
    }
}
