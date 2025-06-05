package com.lazymind.java_in_readme_backend.utility;

import java.util.Map;
import java.util.TreeMap;

public class Utility {

    /*
     * @param message message
     * @param data data of any type of object
     * @param success true of false
     * @return a TreeMap<String,Object> after setting those values
     */
    public static Map<String, Object> createBasicResponse(String message, Object data, boolean success){
        final Map<String, Object> responseMap = new TreeMap<>();
        responseMap.put("message", message);
        responseMap.put("data", data);
        responseMap.put("success", success);
        return responseMap;
    }

}
