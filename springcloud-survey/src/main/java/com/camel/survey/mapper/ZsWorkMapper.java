package com.camel.survey.mapper;

import com.camel.survey.model.ZsSign;
import com.camel.survey.model.ZsWork;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.vo.ProjectReport;
import org.springframework.stereotype.Repository;
import feign.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 访员工作记录 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-03-14
 */
@Repository
public interface ZsWorkMapper extends BaseMapper<ZsWork> {
    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsWork> list(@Param("entity")ZsWork entity, @Param("ids")String[] ids);

    ProjectReport projectReport(Integer proId);

    ProjectReport selectTotalInfoByWork(ZsWork entity);

    String selectTimeRange();

    Map<String, String> selectSharer(@Param("uname") String uname, @Param("idNum") String idNum);
}
