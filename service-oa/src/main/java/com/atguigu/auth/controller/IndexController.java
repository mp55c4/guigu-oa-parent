package com.atguigu.auth.controller;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.common.utils.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.LoginVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags="后台登录管理")
@RestController
@RequestMapping("admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysuserService;
    @Autowired
    private SysMenuService sysMenuService;
    //login
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
//        Map<String,Object> map = new HashMap<>();
//        map.put("token","admin-token");
//        return Result.ok(map);
        //1.获取输入用户名和密码

        //2.根据用户名查询数据库
         String username = loginVo.getUsername();
         LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(SysUser::getUsername,username);
         SysUser sysUser = sysuserService.getOne(wrapper);
        //3.用户信息是否存在
        if(sysUser == null){
            throw new GuiguException(201,"用户不存在");
        }
        //4.判断密码
        String password_db = sysUser.getPassword();
        //获取输入的密码,然后进行加密
        String password_input = MD5.encrypt(loginVo.getPassword());
        if(!password_input.equals(password_db)){
            throw new GuiguException(201,"密码不正确");
        }
        //5.判断用户是否被禁用
        if(sysUser.getStatus() == 0){
            throw new GuiguException(201,"用户已被禁用");
        }
        //6.使用jwt根据用户id和密码生成一个token字符串
        String token =
        JwtHelper.createToken(sysUser.getId(),sysUser.getUsername());
        //7.返回
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("userId",sysUser.getId());
        return Result.ok(map);
    }
    //info
    @GetMapping("info")
    public Result info(HttpServletRequest request) throws IOException {
        //1.从请求头获取用户信息，（获取请求头token字符串）
        String token = request.getHeader("token");
        //2.从token字符串获取用户id或者用户名称
        Long userId = JwtHelper.getUserId(token);
        //3.根据用户id查询数据库，把用户信息获取出来
        SysUser sysUser = sysuserService.getById(userId);
        //4.根据用户id获取用户可以操作菜单列表
        //查询数据库动态构建路由结构，动态显示
        List<RouterVo> routerList = sysMenuService.findUserMenuListByUserId(userId);

        //5，根据用户id获取用户可以操作菜单列表
        List<String> permsList = sysMenuService.findUserPermsByUserId(userId);
        //6.返回相应的数据
        Map<String,Object> map = new HashMap<>();
        map.put("buttons",permsList);
        map.put("roles","[admin]");
        map.put("name",sysUser.getName());
        map.put("avatar","https://img.picgo.net/2024/05/06/Snipaste_2024-05-06_12-50-5526653248fc6ad7b9.jpeg");
        //返回用户可以返回菜单
        map.put("routers",routerList);
        //返回用户可以操作按钮

        return Result.ok(map);
    }
    //logout
    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}
