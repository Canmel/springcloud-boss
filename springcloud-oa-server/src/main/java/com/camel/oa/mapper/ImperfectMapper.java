package com.camel.oa.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.camel.oa.model.Imperfect;
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
 * <差程补全信息>
 * @author baily
 * @since 1.0
 * @date 2019/7/18
 **/
@Mapper
@Repository
public interface ImperfectMapper extends BaseMapper<Imperfect> {

    List<Imperfect> list(Imperfect imperfect);
}
