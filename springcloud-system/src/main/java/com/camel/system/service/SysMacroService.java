package com.camel.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.camel.core.model.SysMacro;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-15
 */
public interface SysMacroService extends IService<SysMacro> {
    /**
     * 分页查询字典
     * @param entity
     * @return
     */
    PageInfo<SysMacro> selectPage(SysMacro entity);
}
