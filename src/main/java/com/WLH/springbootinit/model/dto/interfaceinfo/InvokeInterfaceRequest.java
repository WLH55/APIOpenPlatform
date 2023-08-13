package com.WLH.springbootinit.model.dto.interfaceinfo;

import lombok.Data;

/**
 * @author WLH
 * @verstion 1.0
 */
@Data
public class InvokeInterfaceRequest {
    /**
     * 主键
     */
    private Long id;

    /**
     * 请求参数
     */
    private String requestParams;
}
