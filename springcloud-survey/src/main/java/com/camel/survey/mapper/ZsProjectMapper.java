package com.camel.survey.mapper;

import com.camel.survey.model.ZsProject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2019-12-04
 */
@Repository
public interface ZsProjectMapper extends BaseMapper<ZsProject> {

    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsProject> list(ZsProject entity);

    /**
     * 根据用户查询列表
     * @param uid
     * @return
     */
    List<ZsProject> listByUid(Integer uid);

    ZsProject selectProjectById(Integer id);

}
