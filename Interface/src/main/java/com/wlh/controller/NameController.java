package com.wlh.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.server.HttpServerRequest;
import com.wlh.model.User;
import com.wlh.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


/**
 * @author WLH
 * @verstion 1.0
 */
@RestController
@RequestMapping("/")
@Slf4j
public class NameController {

    @GetMapping("/name")
    public String getNameByGet(String name,HttpServletRequest request) {
        System.out.println(request.getHeader("WLH"));
        return "发送GET请求 你的名字=" + name;
    }

    @PostMapping()
    public String getNameByPost(@RequestParam(value = "name") String name) {
        return "发送POST请求 你的名字是：" + name;
    }

    @PostMapping("/user")
        public String getNameByPostWithJson(@RequestBody User user, HttpServletRequest request) throws UnsupportedEncodingException {

        String accessKey = request.getHeader("accessKey");
        // 防止中文乱码
        String body = request.getHeader("body");
        String sign = request.getHeader("sign");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        boolean hasBlank = StrUtil.hasBlank(accessKey, body, sign, nonce, timestamp);
        // 判断是否有空
        if (hasBlank) {
            return "无权限";
        }
        // TODO 使用accessKey去数据库查询secretKey
        // 假设查到的secret是abc 进行加密得到sign
        String secretKey = "abc";
        String sign1 = SignUtil.getSign(body, secretKey);
        log.info("sign1 = " + sign1);
        log.info("sign = " + sign);
        if(StrUtil.equals(sign, sign1)){
            return "签名错误";
        }

        // TODO 判断随机数nonce
        if(nonce.length() > 5){
            return "无权限";
        }
        // 时间戳是否为数字
        if (!NumberUtil.isNumber(timestamp)) {
            return "无权限";
        }
        // 五分钟内的请求有效
        if (System.currentTimeMillis() - Long.parseLong(timestamp) > 5 * 60 * 1000) {
            return "无权限";
        }
        return "发送POST请求 JSON中你的名字是：" + user.getName();
    }


}
