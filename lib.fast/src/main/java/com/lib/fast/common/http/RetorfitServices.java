package com.lib.fast.common.http;

import java.util.HashMap;
import java.util.Map;

/**
 * created by siwei on 2018/5/23
 */
public class RetorfitServices {

    private static Map<Class, Object> serviceGroup =  new HashMap<>();

    /**获取接口服务*/
    public synchronized static <T> T getService(Class<T> service){
        if(!serviceGroup.containsKey(service)){
            Object serviceObject = HttpFactory.instance().createApiService(service);
            serviceGroup.put(service, serviceObject);
        }
        return (T) serviceGroup.get(service);
    }
}
