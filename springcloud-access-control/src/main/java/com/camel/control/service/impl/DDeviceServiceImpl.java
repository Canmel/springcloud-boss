package com.camel.control.service.impl;

import com.camel.control.model.DDevice;
import com.camel.control.mapper.DDeviceMapper;
import com.camel.control.service.DDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-03-20
 */
@Service
public class DDeviceServiceImpl extends ServiceImpl<DDeviceMapper, DDevice> implements DDeviceService {

    @Autowired
    private DDeviceMapper mapper;

    @Override
    public Boolean save(DDevice device) {
        if(!ObjectUtils.isEmpty(device) && !ObjectUtils.isEmpty(device.getDeviceNumber())) {
            DDevice dDevice = mapper.selectOne(new DDevice(device.getDeviceNumber()));
            if(ObjectUtils.isEmpty(dDevice)) {
                return mapper.insert(device) > 0;
            }else {
                device.setId(dDevice.getId());
                device.setUpdateAt(new Date());
                return mapper.updateById(device) > 0;
            }
        }
        return false;
    }
}
