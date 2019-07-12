package com.camel.oa.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.oa.model.Errand;
import com.camel.oa.model.Reimbursement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <出差MAPPER>
 * @author baily
 * @since 1.0
 * @date 2019/7/8
 **/
@Mapper
@Repository
public interface ErrandMapper extends BaseMapper<Errand> {
    /**
     * 查询出差申请列表
     * @param entity
     * @return
     */
    List<Errand> list(Errand entity);

    /**
     * 未完善的出差申请单
     * @param id
     * @param status
     * @return
     */
    List<Errand> imperfect(Integer id, Integer status);

}
