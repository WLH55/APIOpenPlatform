    package com.WLH.springbootinit.service.impl;

    import com.WLH.springbootinit.service.UserInterfaceInfoService;
    import org.junit.jupiter.api.Test;
    import org.springframework.boot.test.context.SpringBootTest;

    import javax.annotation.Resource;


    /**
     * @author WLH
     * @verstion 1.0
     */
    @SpringBootTest
    class UserInterfaceInfoServiceImplTest {
        @Resource
        private UserInterfaceInfoService  userInterfaceInfoService;

        @Test
        void invokeInterfaceCount() {
            userInterfaceInfoService.invokeInterfaceCount(1,1);

        }
    }