package com.camel.oa.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.oa.model.Imperfect;
import com.camel.oa.model.Reimbursement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-07-12
 */
@Mapper
@Repository
public interface ImperfectMapper extends BaseMapper<Imperfect> {

    List<Imperfect> list(Imperfect imperfect);
}
