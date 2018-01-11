package cn.payjs.java.util;

import org.springframework.util.DigestUtils;

import java.util.*;

public class SignUtil {

    public static String sign(Map<String,String> map, String privateKey){
        Collection<String> keyset= map.keySet();
        List<String> keyList= new ArrayList<>(keyset);
        Collections.sort(keyList);
        StringBuilder sb = new StringBuilder();
        for (String key : keyList){
            sb.append(key).append("=").append(map.get(key)).append("&");
        }
        sb.append("key=").append(privateKey);
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
    }

}
