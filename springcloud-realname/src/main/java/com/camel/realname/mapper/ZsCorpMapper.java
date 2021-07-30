package com.camel.realname.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.realname.model.ZsCorp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * @param status 认证状态
     * @return 返回成功新增的数量
     */
    Integer insertDemo(@Param("userId") Integer userId, @Param("status") Integer status);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 企业认证信息对象
     */
    ZsCorp getOneByUid(Integer userId);

    /**
     * 审核改变状态
     * @param zsCorp 企业认证信息
     */
    Integer audit(ZsCorp zsCorp);

    /**
     * 查询企业认证信息集合（status > 1）
     * @param zsCorp 查询条件
     * @return 企业认证信息集合
     */
    List<ZsCorp> getList(ZsCorp zsCorp);

}