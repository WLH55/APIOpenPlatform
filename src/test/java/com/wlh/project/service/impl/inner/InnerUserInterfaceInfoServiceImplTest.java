package com.wlh.project.service.impl.inner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.wlh.wlhcommon.service.InnerUserInterfaceInfoService;

import javax.annotation.Resource;

/**
 * @author WLH
 * @verstion 1.0
 */
@SpringBootTest
class InnerUserInterfaceInfoServiceImplTest {
    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Test
    void invokeCount() {
        innerUserInterfaceInfoService.invokeCount(1L,1L);

    }
}