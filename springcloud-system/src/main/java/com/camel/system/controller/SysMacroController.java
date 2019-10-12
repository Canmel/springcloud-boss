package com.camel.system.controller;


import com.baomidou.mybatisplus.service.IService;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.core.enums.MacroStatus;
import com.camel.core.model.SysMacro;
import com.camel.system.service.SysMacroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * <字典控制器>
 * @author baily
 * @since 1.0
 * @date 2019/7/5
 **/
@RestController
@RequestMapping("/sysMacro")
public class SysMacroController extends BaseCommonController {
    @Autowired
    private SysMacroService service;

    @GetMapping()
    public Result index(SysMacro macro){
        macro.setStatus(MacroStatus.NORMAL.getCode());
        return ResultUtil.success(service.selectPage(macro));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @GetMapping("/{id}")
    public Result details(@PathVariable Integer id) {
        return super.details(id);
    }

    @PostMapping
    public Result save(@RequestBody SysMacro entity) {
        return super.save(entity);
    }

    @PutMapping
    public Result update(@RequestBody SysMacro entity) {
        return super.update(entity);
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "字典";
    }
}

