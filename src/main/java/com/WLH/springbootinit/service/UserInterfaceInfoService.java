package com.WLH.springbootinit.service;

import com.WLH.springbootinit.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author WLH
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2023-08-13 14:02:55
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);
    boolean invokeInterfaceCount(long userId, long interfaceInfoId);
}
