package com.WLH.springbootinit.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 通过id发送请求
 *
 * @author WLH
 */
@Data
public class InterfaceInforIdRequest implements Serializable {



    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;

}