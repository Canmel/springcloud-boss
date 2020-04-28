package com.camel.survey.service;

import com.camel.survey.model.ZsWorkShift;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

import java.security.Principal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-04-14
 */
public interface ZsWorkShiftService extends IService<ZsWorkShift> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<ZsWorkShift> selectPage(ZsWorkShift entity, Principal principal);



}
