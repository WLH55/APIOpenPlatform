package com.WLH.springbootinit.service;

import com.WLH.springbootinit.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author WLH
 * @description 针对表【interface_info(接口信息)】的数据库操作Service
 * @createDate 2023-08-07 15:17:45
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

}