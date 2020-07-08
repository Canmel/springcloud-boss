package com.camel.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.system.mapper.SysLogMapper;
import com.camel.core.model.SysLog;
import com.camel.system.service.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-09
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    public static final String LOG = "log";

    @Autowired
    private SysLogMapper mapper;

    @Override
    public PageInfo<SysLog> pageQuery(SysLog entity) {
        PageInfo pageInfo = PageHelper.startPage(entity.getPageNum(), entity.getPageSize()).doSelectPageInfo(() -> mapper.list(entity));
        return pageInfo;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void add(HashMap<String, Object> map) {
        if (map.containsKey(LOG) && !ObjectUtils.isEmpty(map.get(LOG))) {
            List<Object> list = (List<Object>) map.get(LOG);
            SysLog log = new SysLog((Integer) list.get(0),(String) list.get(1),(String) list.get(2),(Long) list.get(3),(String) list.get(4),(String) list.get(5),(String) list.get(6),(String) list.get(7));
            insert(log);
        }
    }
}
