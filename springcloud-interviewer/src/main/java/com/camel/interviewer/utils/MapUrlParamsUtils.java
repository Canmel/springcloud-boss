package com.camel.interviewer.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapUrlParamsUtils {
    /**
     * 将url参数转换成map
     *
     * @param param aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, Object> getUrlParams(String param) {
        Map<String, Object> map = new HashMap<String, Object>(0);
        if (StringUtils.isBlank(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 将map转换成url
     * 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        Object[] keys = map.keySet().toArray();
        StringBuffer sb = new StringBuffer();
        Arrays.sort(keys);
        for (Object key: keys) {
            sb.append(key + "=" + map.get(key));
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }
}
