package com.wlh.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wlh.model.User;
import com.wlh.utils.SignUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WLH
 * @verstion 1.0
 */
public class HttpRequestClient {

    private final String accessKey;
    private final String secretKey;
    public HttpRequestClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    public String getNameByGet(String name) {
        return HttpUtil.get("http://localhost:8123/api/name/" +name);
    }


    public String getNameByPost(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        return HttpUtil.post("http://localhost:8123/api/name" ,paramMap);
    }
    public String getNameByPostWithJson(User user) throws UnsupportedEncodingException {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.post("http://localhost:8123/api/name/user")
                .addHeaders(getHeaders(json))
                .body(json)
                .execute();
        System.out.println("response = " + response);
        System.out.println("status = " + response.getStatus());
        if (response.isOk()) {
            return response.body();
        }
        return "fail";
    }
    private Map<String, String> getHeaders(String body) throws UnsupportedEncodingException {
        Map<String,String> header = new HashMap<>();
        header.put("accessKey", accessKey);
        header.put("sign", SignUtil.getSign(body, secretKey));
        //防止中文乱码
        header.put("body", URLEncoder.encode(body, StandardCharsets.UTF_8.name()));
        header.put("nonce", RandomUtil.randomNumbers(4));
        header.put("timestamp",String.valueOf(System.currentTimeMillis()));
        return header;

    }
}
