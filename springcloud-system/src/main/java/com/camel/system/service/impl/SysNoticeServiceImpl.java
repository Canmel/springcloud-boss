package com.camel.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.system.mapper.SysNoticeMapper;
import com.camel.system.model.SysNotice;
import com.camel.system.service.MqService;
import com.camel.system.service.SysNoticeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-09-29
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {
    @Autowired
    private SysNoticeMapper mapper;

    @Autowired
    private MqService mqService;

    @Override
    public PageInfo<SysNotice> selectPage(SysNotice sysNotice) {
        PageInfo pageInfo = PaginationUtil.startPage(sysNotice, () -> {
            mapper.list(sysNotice);
        });
        return pageInfo;
    }

    @Override
    public boolean delete(Serializable serializable) {
        SysNotice sysMenu = new SysNotice((Integer) serializable, 0);
        return mapper.updateById(sysMenu) > -1;
    }

    @Override
    public boolean top(Integer id) {
        Integer maxOrderNum = mapper.selectMaxOrderNum();
        SysNotice sysNotice = mapper.selectOne(new SysNotice(id));
        sysNotice.setOderNum(maxOrderNum + 1);
        mapper.updateById(sysNotice);
        return true;
    }

    @Override
    public boolean push(Integer id) {
        // 推送消息
        SysNotice notice = mapper.selectById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", notice.getTitle());
        jsonObject.put("context", notice.getContent());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("ahdfoagfqhdhiopq");
        jsonArray.add("sadfajbadbuakgdfl");
        jsonObject.put("target", jsonArray);
        jsonObject.toString();
        mqService.sendForNotice(jsonObject.toString());
        return false;
    }
}
