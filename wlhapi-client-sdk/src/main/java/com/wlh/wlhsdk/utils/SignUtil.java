package com.wlh.wlhsdk.utils;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author WLH
 * @verstion 1.0
 */
public class SignUtil {
    public static String getSign(String body, String secretKey) {
        String content = body + "." + secretKey;
        return DigestUtil.md5Hex(content);
    }
}
