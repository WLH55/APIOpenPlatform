package com.wlh.project.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WLH
 * @verstion 1.0
 */
public enum InterfaceInfoStatusEnum {

    OFFLINE("下线",0),
    ONLINE("上线",1);





    private final String text;

    private final int value;
    InterfaceInfoStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     *
     * @return 获取值列表
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
