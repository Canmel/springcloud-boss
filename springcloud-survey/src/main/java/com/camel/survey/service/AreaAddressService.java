package com.camel.survey.service;

import com.camel.survey.model.AreaAddress;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-12-02
 */
public interface AreaAddressService extends IService<AreaAddress> {

    PageInfo<AreaAddress> pageQuery(AreaAddress entity);

    List<AreaAddress> selectMatch(String text, Integer areaId);
}
