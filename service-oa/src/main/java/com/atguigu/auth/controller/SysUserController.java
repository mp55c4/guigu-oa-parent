package com.atguigu.auth.controller;


import com.atguigu.auth.service.SysRoleService;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.result.Result;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2024-04-13
 */
@Api(tags = "系统用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
@CrossOrigin
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    private SysRoleService sysRoleService;
    //用户条件分页查询
    @PreAuthorize("hasAnyAuthority('bnt.sysUser.list')")
    @ApiOperation("用户条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        SysUserQueryVo sysUserQueryVo){
        //1.创建一个Page对象，传递分页相关参数
        Page<SysUser> pageParam = new Page<SysUser>(page,limit);
        //2.封装条件，判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>();
        //获取条件值
        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();

        if(!StringUtils.isEmpty(username)){
            wrapper.like(SysUser::getUsername,username);
        }
        if(!StringUtils.isEmpty(createTimeBegin)){
            wrapper.ge(SysUser::getCreateTime,createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)){
            wrapper.le(SysUser::getCreateTime,createTimeBegin);
        }
        Page<SysUser> page1 = sysUserService.page(pageParam, wrapper);
        return Result.ok(page1);
    }
    @ApiOperation("获取用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }
    @PreAuthorize("hasAnyAuthority('bnt.sysUser.add')")
    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser user){
        String password = user.getPassword();
        String encrypt = MD5.encrypt(password);
        user.setPassword(encrypt);
        sysUserService.save(user);
        return Result.ok();
    }
    @PreAuthorize("hasAnyAuthority('bnt.sysUser.update')")
    @ApiOperation("更新用户")
    @PutMapping("update")
    public Result update(@RequestBody SysUser user){
        sysUserService.updateById(user);
        return Result.ok();
    }
    @PreAuthorize("hasAnyAuthority('bnt.sysUser.remove')")
    @ApiOperation("删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        sysUserService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,@PathVariable Integer status){
        sysUserService.updateStatus(id,status);
        return Result.ok();
    }
    @ApiOperation(value = "获取当前用户基本信息")
    @GetMapping("getCurrentUser")
    public Result getCurrentUser() {
        return Result.ok(sysUserService.getCurrentUser());
    }
}

