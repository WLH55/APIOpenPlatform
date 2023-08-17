package com.wlh.wlhInterface.controller;

import com.wlh.wlhsdk.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author WLH
 * @verstion 1.0
 */
@RestController
@RequestMapping("/name")
@Slf4j
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name, HttpServletRequest request) {
        System.out.println(request.getHeader("WLH"));
        return "发送GET请求 你的名字=" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam(value = "name") String name) {
        return "发送POST请求 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getNameByPostWithJson(@RequestBody User user, HttpServletRequest request) {

        return "发送POST请求 JSON中你的名字是：" + user.getUsername();
    }


}
