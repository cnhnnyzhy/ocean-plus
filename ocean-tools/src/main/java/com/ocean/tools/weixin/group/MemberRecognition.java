package com.ocean.tools.weixin.group;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Description:
 * @Author: yang.zhang
 * @Date: 2022/7/16 17:17
 */
@Slf4j
public class MemberRecognition {

    private static final String YOUDAO_URL = "https://openapi.youdao.com/ocrapi";

    private static final String APP_KEY = "535b9b6d064965eb";

    private static final String APP_SECRET = "PpqfYTED6gbvrmcm86OlB19d0Js7699T";
    private static final List<String> INVALID_DATA_LIST = Lists.newArrayList(
            "“l N", "聊天成员(500)", "D1 52%D 11:01", "<", "删除", "Q搜索", "。", "O A", "Ο ا",
            "Οه", "Ο و", "ه", "و"
            , "- ΣZΟPORS > 3X>"
            , "- ORs"
            , ">w x"
            , "|"
            , "| A BC ০ ৩"
            , "ABCDE-"
            , "ΣZΟ ORS P>"
            , "->x"
            , "ΣZOP ORS- ১>w>"
            , "ABD"
            , "οم α -"
            , "-১>wx> N #",
            "৩", "O و",
            "- ΣZΟP ORS- D>WX>N #",
            "|ABC D ULY - ΣZOD Oαഗ",
            "|>X> N#",
            "| T ΣZOPQRS - D>Wx>",
            "| ABC O - ৩",
            "Σ ZO PORS -O>Sx>",
            "Y-ΣZΟP σα",
            "•>X>N #",
            "Ο Υ - ΣZΟPORS- D> WXYN=",
            "ய", "0",
            "ΟΥ ΣZΟΡORS -D>X>N #",
            "| AB ΣZOAORS-D>WX>N #",
            "|AB L ৩",
            "צ - ΣZΟDΟRSHO> 3 X>",
            "A B",
            "> wx> N #",
            "ORs",
            ">wx",
            "|ABc",
            "|4BC০ L ৩",
            ">3×"
    );

    private static final List<String> INDEX_OF_DATA_LIST = Lists.newArrayList("*153%", "Σ", "৩", "w>", ">w", "ட-", "ပ০", "C০", "ய", "ل",
            "*স", "");

    public static void main(String[] args) throws IOException {
        String rootPath = "/Users/ocean/Documents/temp/xitang/";
        String group = "Go";
        File dir = new File(rootPath + group);
        List<String> list = new ArrayList<>();

        Arrays.stream(dir.listFiles((file) -> {
                    String suffix = FileUtil.getSuffix(file);
                    return suffix.equalsIgnoreCase("jpeg");
                }

        )).sorted((o1, o2) -> {
            return o1.compareTo(o2);
        }).forEach(file -> {
            try {
                Optional.ofNullable(recognition(file.getPath())).ifPresent(subList -> list.addAll(subList));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        FileUtil.writeLines(list, new File(rootPath + group + "/" + group + "-"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))), "utf-8");
        System.out.println("success");
    }

    private static List<String> recognition(String filePath) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        String q = loadAsBase64(filePath);
        String salt = String.valueOf(System.currentTimeMillis());
        String detectType = "10012";
        String imageType = "1";
        String langType = "auto";
        params.put("detectType", detectType);
        params.put("imageType", imageType);
        params.put("langType", langType);
        params.put("img", q);
        params.put("docType", "json");
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("salt", salt);
        params.put("sign", sign);
        String result = requestForHttp(YOUDAO_URL, params);
        if (StringUtils.isBlank(result)) {
            return null;
        }
        System.out.println(result);
        JSONObject ret = JSON.parseObject(result);
        if (!StringUtils.equals(ret.getString("errorCode"), "0")) {
            return null;
        }
        List<String> list = new ArrayList<>();
        Optional.ofNullable(ret.getJSONObject("Result")).ifPresent(
                obj -> {
                    Optional.ofNullable(obj.getJSONArray("regions")).ifPresent(regions -> {
                        for (int i = 0; i < regions.size(); i++) {
                            JSONObject region = regions.getJSONObject(i);
                            Optional.ofNullable(region.getJSONArray("lines")).ifPresent(lines -> {
                                for (int j = 0; j < lines.size(); j++) {
                                    Optional.ofNullable(lines.getJSONObject(j)).ifPresent(line -> {
                                        String text = line.getString("text");
                                        if (INVALID_DATA_LIST.contains(text)) {
                                            return;
                                        }
                                        for (String str : INDEX_OF_DATA_LIST) {
                                            if (text.indexOf(str) != -1) {
                                                return;
                                            }
                                        }
                                        list.add(text);
                                    });
                                }
                            });
                        }
                    });
                }
        );
        /** 处理结果 */
        // System.out.println(result);
        return list;
    }

    public static String requestForHttp(String url, Map<String, String> params) throws IOException {
        String result = "";

        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key, value));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "UTF-8");
            EntityUtils.consume(httpEntity);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                log.info("## release resouce error ##" + e);
            }
        }
        return result;
    }

    /**
     * 生成加密字段
     */
    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes();
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String loadAsBase64(String imgFile) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理

        File file = new File(imgFile);
        if (!file.exists()) {
            log.error("文件不存在");
            return null;
        }
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        return Base64.getEncoder().encodeToString(data);//返回Base64编码过的字节数组字符串
    }

    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        String result;
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }
}
