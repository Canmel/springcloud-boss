package com.camel.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;

/** @author baily */
public interface BaseEnum extends IEnum {
    /**
     * 获取枚举值
     * @return
     */
    @Override
    Integer getValue();
}
