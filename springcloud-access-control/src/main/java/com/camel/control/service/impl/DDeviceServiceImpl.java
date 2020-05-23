package com.camel.control.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.camel.control.model.DDevice;
import com.camel.control.mapper.DDeviceMapper;
import com.camel.control.model.ResultAccess;
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

    @Override
    public ResultAccess findDDeviceByDeviceNumber(String deviceNumber, String msg) {
        ResultAccess result = new ResultAccess();
        result.setMsg(msg);
        Wrapper<DDevice> wrapper = new EntityWrapper();
        wrapper.eq("device_number",deviceNumber);
        Integer count = mapper.selectCount(wrapper);
        if(count>=1){
            result.setCode(200);
        }
        if(!ObjectUtils.isEmpty(msg)&& msg.equals("认证成功")){
            result.setPersonnelType("0");
        }else{
            result.setPersonnelType("2");
        }
        return result;
    }
}
