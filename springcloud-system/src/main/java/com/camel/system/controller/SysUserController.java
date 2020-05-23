package com.camel.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.common.entity.Role;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.model.SysRole;
import com.camel.core.model.SysUserRole;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.entity.RedisUser;
import com.camel.redis.utils.SerizlizeUtil;
import com.camel.system.annotation.Log;
import com.camel.core.model.SysUser;
import com.camel.system.config.SysUserCacheConfig;
import com.camel.system.service.SysRoleService;
import com.camel.system.service.SysUserRoleService;
import com.camel.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

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
 * <用户控制器>
 * @author baily
 * @since 1.0
 * @date 2019/7/5
 **/
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseCommonController {

    @Autowired
    private SysUserService service;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserCacheConfig sysUserCacheConfig;

    @Log(moduleName = "用户", option = "查询列表")
    @GetMapping
    public Result index(SysUser sysUser) {
        return ResultUtil.success(service.pageQuery(sysUser));
    }

    @PostMapping
    public Result save(@RequestBody SysUser sysUser) {
        return super.save(sysUser);
    }

    @PostMapping("/interviewer")
    public Result interviewer(@RequestBody SysUser sysUser) {
        return service.interviewer(sysUser);
    }

    @GetMapping("/{id}")
    public Result save(@PathVariable(required = true) Integer id) {
        Result result = super.details(id);
        SysUser user = (SysUser) result.getData();
        service.getRolesByUser(user).setPassword("");
        return ResultUtil.success(user);
    }

    @PutMapping
    public Result update(@RequestBody SysUser sysUser) {
        Result result = super.update(sysUser);
        sysUserCacheConfig.initSysUsers();
        return result;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable(required = true) Integer id) {
        return ResultUtil.success(super.delete(id));
    }

    @GetMapping("/valid/{name}")
    public Result nameValid(@PathVariable String name, Integer id) {
        return ResultUtil.success(service.exist(name, id));
    }

    @PostMapping("/roles")
    public Result addRole(@RequestBody SysUser user) {
        if (service.addRoles(user)) {
            return ResultUtil.success("修改用户角色成功");
        }
        return ResultUtil.error(400, "修改用户角色失败");
    }

    @GetMapping("/me")
    public Result me() {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        byte[] cu = (byte[]) operations.get("CURRENT_USER");
        RedisUser sysUser = (RedisUser) SerizlizeUtil.unserizlize(cu);
        return ResultUtil.success(sysUser);
    }

    @GetMapping("/all")
    public Result all(SysUser user){
        return ResultUtil.success(service.all());
    }

    /**
     * 查询指定角色的所有用户
     * @return
     */
    @GetMapping("/role/{id}")
    public Result byRole(@PathVariable String id){
        return ResultUtil.success(service.byRole(Integer.parseInt(id)));
    }

    @PutMapping("/avatar")
    public Result avatar(@RequestBody SysUser sysUser) {
        super.update(sysUser);
        return ResultUtil.success("修改用户头像成功");
    }

    @GetMapping("/byIdNum/{idNum}")
    public SysUser oneUser(@PathVariable String idNum){
        Wrapper<SysUser> userWrapper = new EntityWrapper<>();
        userWrapper.eq("id_num", idNum);
        return service.selectOne(userWrapper);
    }

    public static final String QUEUE_NAME = "ActiveMQ.System.New.User";

    @JmsListener(destination = QUEUE_NAME)
    public void newNormalUser(SysUser sysUser) {
        System.out.println(sysUser);
        if(ObjectUtils.isEmpty(sysUser.getUid())) {
            service.insert(sysUser);
            Wrapper<SysRole> roleWrapper = new EntityWrapper<>();
            roleWrapper.eq("role_name", "interviewer");
            SysRole role = roleService.selectOne(roleWrapper);
            SysUserRole sysUserRole = new SysUserRole(sysUser.getUid(), role.getRoleId());
            userRoleService.insert(sysUserRole);
            return;
        }
        Wrapper<SysUser> userWrapper = new EntityWrapper<>();
        userWrapper.eq("id_num", sysUser.getIdNum());
        SysUser current = service.selectOne(userWrapper);
        sysUser.setUid(current.getUid());
        service.updateById(sysUser);
    }

    @GetMapping("/{id}/selectByUid")
    public Result selectByUid(@PathVariable Integer id) {
        return ResultUtil.success(service.selectByUid(id));
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "用户";
    }
}

