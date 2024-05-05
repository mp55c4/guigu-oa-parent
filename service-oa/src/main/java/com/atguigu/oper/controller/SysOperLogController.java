package com.atguigu.oper.controller;


import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysOperLog;
import com.atguigu.oper.service.SysOperLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2024-05-05
 */
@Api(tags="操作日志记录管理")
@RestController
@RequestMapping("/admin/system/sysOper")
@CrossOrigin
public class SysOperLogController {
    @Autowired
    private SysOperLogService sysOperLogService;
    //分页获取操作日志
    @ApiOperation(value = "分页获取操作日志")
    @GetMapping("{page}/{limit}")
    public Result pageQueryOper(@PathVariable Long page,
                                @PathVariable Long limit, SysOperLog sysOperLog){
        //调用service方法实现
        //创建page对象
        Page<SysOperLog> pageParms = new Page<>(page, limit);
        //封装条件
        LambdaQueryWrapper<SysOperLog> wrapper = new LambdaQueryWrapper<>();
        String operUser = sysOperLog.getOperUser();
        if(!StringUtils.isEmpty(operUser)){
            wrapper.like(SysOperLog::getOperUser,operUser);
        }
        //封装条件，判断条件是否为空，不为空进行封装
        Page<SysOperLog> page1 = sysOperLogService.page(pageParms,wrapper);
        return Result.ok(page1);
    }
}

