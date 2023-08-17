package com.wlh.project.service.impl.inner;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wlh.project.common.ErrorCode;
import com.wlh.project.exception.BusinessException;
import com.wlh.project.mapper.UserInterfaceInfoMapper;
import com.wlh.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.wlh.wlhcommon.model.entity.UserInterfaceInfo;
import org.wlh.wlhcommon.service.InnerUserInterfaceInfoService;

import javax.annotation.Resource;

/**
 * 内部用户接口信息服务实现类
 *
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    /**
     * 调用接口统计
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeInterfaceCount(userId,interfaceInfoId);
    }

    /**
     * 是否还有调用次数
     *
     * @param interfaceInfoId 接口id
     * @param userId          用户id
     * @return boolean
     */
    @Override
    public boolean hasInvokeNum(long interfaceInfoId, long userId) {
        if (userId <= 0 || interfaceInfoId <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInterfaceInfo::getUserId, userId)
                .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                .gt(UserInterfaceInfo::getLeftNum, 0);

        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        return userInterfaceInfo != null;
    }

}
