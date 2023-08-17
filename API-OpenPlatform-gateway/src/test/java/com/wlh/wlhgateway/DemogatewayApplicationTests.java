package com.wlh.wlhgateway;

import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.wlh.wlhcommon.service.InnerUserInterfaceInfoService;

@SpringBootTest
class DemogatewayApplicationTests {

    @Test
    void contextLoads() {
    }

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Test
    public void invokeTest(){
        innerUserInterfaceInfoService.invokeCount(1L,1L);
    }


}
