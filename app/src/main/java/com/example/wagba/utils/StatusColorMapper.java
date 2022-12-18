package com.example.wagba.utils;

import android.graphics.Color;
import android.util.Log;

import com.example.wagba.R;

import java.util.Map;
import java.util.HashMap;

public class StatusColorMapper {
    private static Map<String, Integer> statusColorMap;

    static {
        statusColorMap = new HashMap<>();
        statusColorMap.put("placed", R.color.secondary_blue);
        statusColorMap.put("confirmed", R.color.dark_blue);
        statusColorMap.put("completed", R.color.green);
        statusColorMap.put("canceled", R.color.red);
        statusColorMap.put("default", R.color.grey);
    }

    public static Integer getColorForStatus(String status) {
        return statusColorMap.getOrDefault(status, statusColorMap.get("default"));
    }
}
