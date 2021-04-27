package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.SysCompany;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2021-04-27
 */
@Repository
public interface SysCompanyMapper extends BaseMapper<SysCompany> {
    /**
     * 查询列表
     *
     * @param entity
     * @return
     */
    List<SysCompany> list(SysCompany entity);
}
