package com.atguigu.auth.controller;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.ietf.jgss.GSSException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    //1.查询所有角色和当前用户所属角色
    @ApiOperation("获取角色")
    @GetMapping("toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId){
        Map<String,Object> map = sysRoleService.findRoleDataByUserId(userId);
        return Result.ok(map);
    }
    //2.为用户分配角色
    @ApiOperation("为用户分配角色")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo){
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }
//    @GetMapping("findAll")
//    public List<SysRole> findAll() {
//        List<SysRole> roleList = sysRoleService.list();
//        return roleList;
//    }
    //统一返回数据结果
    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public Result findAll(){
        List<SysRole> list = sysRoleService.list();
        try{
            int i = 10/0;
        }catch (Exception e){
            throw new GuiguException(20001,"执行了自定义异常处理");
        }

//        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        //调用mp方法实现查询操作
//        List<SysRole> list1 = sysRoleMapper.selectList(wrapper);
        List<SysRole> list1 = sysRoleService.list();
        return Result.ok(list1);
    }

    //条件分页查询
    //page 当前页 limit每页显示记录数
    //SysRoleQueyVo 条件对象
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit, SysRoleQueryVo sysRoleQueryVo) {

        //调用service的方法实现
        //1.创建一个Page对象，传递分页相关参数
        Page<SysRole> pageParam = new Page<>(page,limit);
        //2.封装条件，判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)){
            //封装
            wrapper.like(SysRole::getRoleName,roleName);
        }
        //3.调用方法查询
        Page<SysRole> page1 = sysRoleService.page(pageParam, wrapper);
        return Result.ok(page1);
    }
    //添加角色
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result save(@RequestBody @Validated SysRole role){
        //调用service添加角色
        boolean is_success = sysRoleService.save(role);
        if(is_success) {
            return Result.ok(role.getRoleName());
        }else{
            return Result.fail();
        }
    }

    //根据id查询
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("通过id进行查询")
    @GetMapping("get/{id}")
    public Result querrybyid(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }
    //修改角色-最终修改
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result update(@RequestBody SysRole role){
        //封装条件
        boolean is_success = sysRoleService.updateById(role);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("删除角色")
    @DeleteMapping("remove/{roleId}")
    public Result delete(@PathVariable Long roleId){
        SysRole sysRole = sysRoleService.getById(roleId);
        boolean is_success = sysRoleService.removeById(roleId);
        if(is_success){
            return Result.ok("删除的角色为:"+sysRole.getRoleName());
        }else{
            return Result.fail();
        }
    }
//    //删除角色，并获取删除角色的值
//    @ApiOperation("删除角色11111")
//    @DeleteMapping("remove/{roleId1}")
//    public String delete1(@PathVariable Long roleId1){
//        SysRole sysRole = sysRoleService.getById(roleId1);
//        boolean is_success = sysRoleService.removeById(roleId1);
//        if(is_success){
//            return "删除成功"+"删除的角色为"+sysRole.getRoleName();
//        }else{
//            return "删除"+sysRole.getRoleName()+"失败";
//        }
//    }
    //批量删除
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){

        boolean is_success = sysRoleService.removeByIds(idList);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
}
