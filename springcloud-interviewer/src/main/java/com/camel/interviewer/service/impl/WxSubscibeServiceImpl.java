package com.camel.interviewer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.interviewer.config.WxConstants;
import com.camel.interviewer.model.WxSubscibe;
import com.camel.interviewer.mapper.WxSubscibeMapper;
import com.camel.interviewer.service.WxSubscibeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 关注用户 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-03-04
 */
@Service
public class WxSubscibeServiceImpl extends ServiceImpl<WxSubscibeMapper, WxSubscibe> implements WxSubscibeService {
    @Override
    public boolean save(String toUserName, String eventKey) {
        String shareName = "";
        if(StringUtils.isNotBlank(eventKey) && StringUtils.contains(eventKey, WxConstants.QRCODE_EVENTKEY)) {
            List<String> params = CollectionUtils.arrayToList(eventKey.split(WxConstants.QRCODE_EVENTKEY));
            shareName = params.get(1);
        }
        return this.insert(new WxSubscibe(toUserName, shareName));
    }

    @Override
    public boolean unsave(String toUserName) {
        Wrapper<WxSubscibe> wrapper = new EntityWrapper<>();
        wrapper.eq("user", toUserName);
        return this.delete(wrapper);
    }
}
