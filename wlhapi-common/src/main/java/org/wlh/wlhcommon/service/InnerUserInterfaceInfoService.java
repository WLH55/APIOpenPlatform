package org.wlh.wlhcommon.service;

/**
 * 内部用户接口信息服务
 * @Author: wlh
 */

public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 是否还有调用次数
     *
     * @param userId          用户id
     * @param interfaceInfoId 接口id
     * @return boolean
     */
    boolean hasInvokeNum(long interfaceInfoId, long userId);
}
