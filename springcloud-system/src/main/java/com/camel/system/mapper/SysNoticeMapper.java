package com.camel.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.system.model.SysNotice;

import java.util.List;

/**
 *　　　　　　　 ┏┓    ┏┓+ +
 *　　　　　　　┏┛┻━━━━┛┻┓ + +
 *　　　　　　　┃        ┃ 　通告MAPPER
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
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
    List<SysNotice> list(SysNotice sysNotice);
}
