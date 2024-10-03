package net.onedsix.ffw4j.core;

import com.google.gson.Gson;

/** A collection of library providers aiming to reduce memory usage by FFw4J
 * @see SingletonLibProvider#getGson() */
public class SingletonLibProvider {
    //
    private static Gson gson;
    //
    public static Gson getGson() {
        if (gson == null) gson = new Gson();
        return gson;
    }
}
