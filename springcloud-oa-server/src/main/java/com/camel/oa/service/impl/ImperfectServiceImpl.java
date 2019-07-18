package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.utils.PaginationUtil;
import com.camel.oa.mapper.ImperfectMapper;
import com.camel.oa.model.Imperfect;
import com.camel.oa.model.Trip;
import com.camel.oa.service.ImperfectService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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
@Service
public class ImperfectServiceImpl extends ServiceImpl<ImperfectMapper, Imperfect> implements ImperfectService {

    @Autowired
    private ImperfectMapper mapper;

    @Override
    public boolean valid(Integer errandId) {
        Wrapper wrapper = new EntityWrapper<Imperfect>();
        wrapper.eq("errand_id", errandId);
        return mapper.selectCount(wrapper) == 0;
    }

    @Override
    public List<Trip> trips(Integer id) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("imperfect_id", id);
        return mapper.selectList(wrapper);
    }

    @Override
    public PageInfo<Imperfect> selectPage(Imperfect imperfect) {
        PageInfo pageInfo = PaginationUtil.startPage(imperfect, () -> {
            mapper.list(imperfect);
        });
        return pageInfo;
    }

    @Override
    public Imperfect getByErrand(Integer id) {
        Imperfect imperfect = new Imperfect();
        imperfect.setId(id);
        return mapper.list(imperfect).get(0);
    }
}
