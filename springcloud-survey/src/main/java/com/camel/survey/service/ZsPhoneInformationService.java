package com.camel.survey.service;

import com.camel.survey.model.ZsPhoneInformation;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-06-10
 */
public interface ZsPhoneInformationService extends IService<ZsPhoneInformation> {

    PageInfo<ZsPhoneInformation> pageQuery(ZsPhoneInformation zsPhoneInformation);

}
