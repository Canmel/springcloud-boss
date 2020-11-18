package com.camel.survey.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.ZsQueue;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-11-02
 */
@Mapper
@Repository
public interface ZsQueueMapper extends BaseMapper<ZsQueue> {
    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<ZsQueue> list(ZsQueue entity);
}
