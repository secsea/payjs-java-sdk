package cn.payjs.java.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class HttpInvoker {

    private static String charset = "utf-8";
    private static String proxyHost = null;
    private static Integer proxyPort = null;

    public static String readContentFromGet(String url) {
        String result = null;
        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
        URL getUrl = null;
        try {
            getUrl = new URL(url);
        } catch (Exception e) {
        }
        // 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
        // URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) getUrl.openConnection();
        } catch (Exception e) {
        }
        // 发送数据到服务器并使用Reader读取返回的数据
        BufferedReader reader = null;
        try {
            // 建立与服务器的连接，并未发送数据
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));
            String lines;
            while ((lines = reader.readLine()) != null) {
                result = lines;
            }
        } catch (Exception e) {
            try {
                if (null != reader)
                    reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            // 断开连接
            connection.disconnect();
        }
        return result;
    }

    public static String readContentFromPostByJSON(String toUrl, JSONObject obj) {
        String result = null;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            // 创建连接
            URL url = new URL(toUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.connect();

            // POST请求
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8");
            out.append(obj.toString());
            out.flush();
            out.close();

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            result = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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

    public static String readContentFromPost(String toUrl, Map<String,String> parameterMap) {
        String result = null;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = ""+parameterMap.get(key);
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
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.connect();

            // POST请求
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            System.out.println("Sending post data:"+parameterBuffer.toString());
            out.append(parameterBuffer.toString());
            out.flush();
            out.close();

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                sb.append(lines);
            }
            result = sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != connection)
                connection.disconnect();
        }
        return result;
    }
}
