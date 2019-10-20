package com.camel.oa.service;

import com.camel.oa.model.Project;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^ *        _-^ *       - *     _/ *    / *   - *  - * - *_#/| *|/ |#/\#/\#/\/  \#/\ *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * < 服务类>
 * @author baily
 * @since 1.0
 * @date 2019-10-20
 **/
public interface ProjectService extends IService<Project> {
    /**
     分页查询
     @param entity
     @return
     */
    PageInfo<Project> selectPage(Project entity);
}
