package com.lazymind.java_in_readme_backend.utility;

import java.util.Map;
import java.util.TreeMap;

public class Utility {

    public static final long TWO_FETCH_MIN_INTERVAL = 60*60*1000L;

    /*
     * @param message
     * @param data of any type of object
     * @param success true of false indicating if read operation success or not
     * @return a TreeMap<String,Object> after setting those values
     */
    public static Map<String, Object> createBasicResponse(String message, Object data, boolean success){
        final Map<String, Object> responseMap = new TreeMap<>();
        responseMap.put("message", message);
        responseMap.put("data", data);
        responseMap.put("success", success);
        return responseMap;
    }

    /**
     *
     * @param timestamp time difference between current time and last refresh time
     * @return the remaining amount to wait before making next call in minute
     */
    public static int findMinAfterCallCanBeMade(long timestamp){
        long remaining = TWO_FETCH_MIN_INTERVAL - timestamp;
        if(remaining <= 0) return 0;

        return (int)(remaining / (60*1000L));
    }

}
