package com.camel.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.core.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *　　　　　　　 ┏┓    ┏┓+ +
 *　　　　　　　┏┛┻━━━━┛┻┓ + +
 *　　　　　　　┃        ┃ 　用户MAPPER
 *　　　　　　　┃   ━    ┃ ++ + + +
 *           ████━████ ┃+
 *　　　　　　　┃        ┃ +
 *　　　　　　　┃   ┻    ┃
 *　　　　　　　┃        ┃ + +
 *　　　　　　　┗━┓   ┏━━┛
 *　　　　　　　  ┃   ┃　　　　　　　　　　
 *　　　　　　　  ┃   ┃ + + + +
 *　　　　　　　  ┃   ┃　　　Code is far away from bug with the animal protecting　　　　　　　
 *　　　　　　　  ┃   ┃+ 　　　　神兽保佑,代码无bug　　
 *　　　　　　　  ┃   ┃
 *　　　　　　　  ┃   ┃　　+　　　　　　　　　
 *　　　　　　　  ┃   ┗━━━━━━━┓ + +
 *　　　　　　　  ┃           ┣┓
 *　　　　　　　  ┃           ┏┛
 *              ┗┓┓┏━━━━━┳┓┏┛ + + + +
 *               ┃┫┫     ┃┫┫
 *               ┗┻┛     ┗┻┛+ + + +
 * @author baily
 * @since  2019/7/4
 */
@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 查询用户列表
     * @param sysUser
     * @return
     */
    List<SysUser> list(SysUser sysUser);

    /**
     * 通过角色获取用户列表
     * @param id
     * @return
     */
    List<SysUser> byRole(Integer id);

    int updateSeat(Integer userId, String seatNum);

    int deleteSeat(String seatNum);

    int loadWorkNum();
}
