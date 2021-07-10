package com.camel.realname.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.realname.model.ZsCorp;

public interface ZsCorpMapper extends BaseMapper<ZsCorp> {
    /**
     * 修改企业认证信息
     * @param zsCorp 企业认证信息
     * @return 返回成功修改的数量
     */
    Integer updateCorp(ZsCorp zsCorp);

    /**
     * 新增一个默认的企业认证信息
     * @param userId 用户id
     * @return 返回成功新增的数量
     */
    Integer insertDemo(Integer userId);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 企业认证信息对象
     */
    ZsCorp getOneByUid(Integer userId);

}
