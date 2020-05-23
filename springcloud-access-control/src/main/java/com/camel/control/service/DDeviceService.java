package com.camel.control.service;

import com.camel.control.model.DDevice;
import com.baomidou.mybatisplus.service.IService;
import com.camel.control.model.ResultAccess;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
public interface DDeviceService extends IService<DDevice> {
    Boolean save(DDevice device);

    /**
     * 根据数据库查询的机器码，和survey获得的信息来判定是否进入
     * @param deviceNumber
     * @param msg
     * @return
     */
    ResultAccess findDDeviceByDeviceNumber(String deviceNumber, String msg);
}
