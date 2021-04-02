package com.camel.interviewer.mapper;

import com.camel.interviewer.entity.wx.WxZc;
import com.camel.interviewer.model.WxUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 微信用户信息 Mapper 接口
 * </p>
 *
 * @author baily
 * @since 2020-03-09
 */
@Repository
public interface WxUserMapper extends BaseMapper<WxUser> {
    /**
     * 查询列表
     * @param entity
     * @return
     */
    List<WxUser> list(WxUser entity);

    List<WxZc> allWxZc();

    List<Map<String, String>> selectRecommends(String openid);

    List<Map<String, String>> selectRecommendSalary(String idNum);
}
