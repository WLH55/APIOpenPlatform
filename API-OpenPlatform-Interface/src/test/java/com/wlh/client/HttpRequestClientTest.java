package com.wlh.client;

import com.wlh.wlhsdk.client.HttpRequestClient;
import com.wlh.wlhsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;


/**
 * @author WLH
 * @verstion 1.0
 */
@SpringBootTest
class HttpRequestClientTest {
    @Resource
    private HttpRequestClient httpRequestClient;

    @Test
    void testAPIClient() throws UnsupportedEncodingException {
        String result = httpRequestClient.getNameByPost("WLH");
        String result2 = httpRequestClient.getNameByGet("丽洪");
        User user = new User();
        user.setName("123");
        String result3 = httpRequestClient.getNameByPostWithJson(user);
        System.out.println("result = " + result);
        System.out.println("result2 = " + result2);
        System.out.println("result3 = " + result3);



    }

}