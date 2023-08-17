package com.wlh.project.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.wlh.wlhcommon.model.entity.InterfaceInfo;

/**
 * 接口信息封装视图
 *
 * @TableName product
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    /**
     * 调用次数
     */
    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}