package com.wlh.project.controller;

import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.wlh.project.annotation.AuthCheck;
import com.wlh.project.common.*;
import com.wlh.project.constant.UserConstant;
import com.wlh.project.exception.BusinessException;
import com.wlh.project.exception.ThrowUtils;
import com.wlh.project.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.wlh.project.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.wlh.project.model.dto.interfaceinfo.InterfaceInforIdRequest;
import com.wlh.project.model.dto.interfaceinfo.InvokeInterfaceRequest;
import com.wlh.project.model.enums.InterfaceInfoStatusEnum;
import com.wlh.project.service.InterfaceInfoService;
import com.wlh.project.service.UserService;
import com.wlh.wlhsdk.client.HttpRequestClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wlh.wlhcommon.model.entity.InterfaceInfo;
import org.wlh.wlhcommon.model.entity.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;
    @Resource
    private HttpRequestClient httpRequestClient;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping ("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);

        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param interfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);

        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }



    /**
     * 上线接口
     *
     * @param interfaceInforIdRequest 携带id
     * @return 是否上线成功
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")//鱼皮自己写的注解，用于管理员校验
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody InterfaceInforIdRequest interfaceInforIdRequest) throws UnsupportedEncodingException {
        if (interfaceInforIdRequest == null || interfaceInforIdRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);

        }

// 判断接口是否存在
        long id = interfaceInforIdRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
// 判断接口是否能使用
// TODO 根据测试地址来调用
// 这里我先用固定的方法进行测试，后面来改
        com.wlhsdk.model.User user = new com.wlhsdk.model.User();
        user.setName("丽洪");
        String name = httpRequestClient.getNameByPostWithJson(user);
        if (StringUtils.isBlank(name)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);

        }
//更新数据库
        InterfaceInfo updateInterfaceInfo = new InterfaceInfo();

        updateInterfaceInfo.setId(id);
        updateInterfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean isSuccessful = interfaceInfoService.updateById(updateInterfaceInfo);
        return ResultUtils.success(isSuccessful);


    }

    /**
     * 下线
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                      HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 在线调用接口
     *
     * @param invokeInterfaceRequest 携带id、请求参数
     * @return data
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterface(@RequestBody InvokeInterfaceRequest invokeInterfaceRequest, HttpServletRequest request)
            throws UnsupportedEncodingException{
        if(invokeInterfaceRequest == null || invokeInterfaceRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断接口是否存在
        // 判断接口是否存在
        long id = invokeInterfaceRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if(interfaceInfo.getStatus() != InterfaceInfoStatusEnum.ONLINE.getValue()){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口未上线");
        }
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        HttpRequestClient httpRequestClient1 = new HttpRequestClient(accessKey, secretKey);
        //先写死请求
        String userRequestParams = invokeInterfaceRequest.getRequestParams();
        com.wlhsdk.model.User user = JSONUtil.toBean(userRequestParams, com.wlhsdk.model.User.class);
        String result = httpRequestClient1.getNameByPostWithJson(user);
        return ResultUtils.success(result);

    }



}
