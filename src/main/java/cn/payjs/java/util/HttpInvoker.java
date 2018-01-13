package cn.payjs.java.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class HttpInvoker {

    private static String charset = "UTF-8";

    public static String readContentFromPost(String toUrl, Map<String, String> parameterMap) {
        String result = null;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder parameterBuffer = new StringBuilder();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key;
            String value;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = "" + parameterMap.get(key);
                } else {
                    value = "";
                }

                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        try {
            // 创建连接
            URL url = new URL(toUrl);
            connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.connect();

            // POST请求
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), charset);
            System.out.println("Sending post data:" + parameterBuffer.toString());
            out.append(parameterBuffer.toString());
            out.flush();
            out.close();

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));
            String lines;
            StringBuilder sb = new StringBuilder("");
            while ((lines = reader.readLine()) != null) {
                sb.append(lines);
            }
            result = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection)
                connection.disconnect();
        }
        return result;
    }
}
