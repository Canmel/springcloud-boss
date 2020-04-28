package com.camel.survey.mapper;

import com.camel.survey.model.ZsWorkRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.survey.model.ZsWorkWorkshift;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-04-27
 */
@Repository
public interface ZsWorkRecordMapper extends BaseMapper<ZsWorkRecord> {

    List<ZsWorkRecord> list(ZsWorkRecord zsWorkRecord);
}
