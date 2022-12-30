package com.example.wagba.utils;

import com.example.wagba.R;

import java.util.HashMap;
import java.util.Map;

public class StatusColorMapper {
    private static final Map<String, Integer> statusColorMap;

    static {
        statusColorMap = new HashMap<>();
        statusColorMap.put("placed", R.color.secondary_blue);
        statusColorMap.put("confirmed", R.color.dark_blue);
        statusColorMap.put("delivered", R.color.green);
        statusColorMap.put("cancelled", R.color.red);
        statusColorMap.put("default", R.color.grey);
    }

    public static Integer getColorForStatus(String status) {
        return statusColorMap.getOrDefault(status, statusColorMap.get("default"));
    }
}
