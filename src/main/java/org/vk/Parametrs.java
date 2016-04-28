package org.vk;

import java.util.HashMap;

/**
 * Created by aleksejpluhin on 16.04.16.
 */
 class Parametrs {
    private final HashMap<String, String> params;

     Parametrs() {
        params = new HashMap<>();
    }


    public String build() {
        if (params.isEmpty()) return "";
        final StringBuilder result = new StringBuilder();
        params.keySet().stream().forEach(key -> {
            result.append(key).append('=').append(params.get(key)).append('&');
        });
        return result.toString();
    }

     void setParams(String key, String value) {
        params.put(key, value);
    }
}

