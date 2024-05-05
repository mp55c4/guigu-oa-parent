package com.atguigu.oper.service.impl;

import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysOperLog;
import com.atguigu.oper.mapper.SysOperLogMapper;
import com.atguigu.oper.service.SysOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2024-05-05
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;
    @Override
    public void saveOperLog(HttpServletRequest request, Result<Result> result){
        //创建日志对象
        SysOperLog sysOperLog = new SysOperLog();
        String JsonResult = new String("{"+"code"+":"+result.getCode()+","+"message"+":"+result.getMessage()+"}");
        //从token中获取用户名
        String token = request.getHeader("token");
        String username = JwtHelper.getUsername(token);
        //从request中获取Url
        String url = request.getRequestURL().toString();
        int index = url.indexOf("/admin");
            // 从"/admin"之后的字符开始截取直到字符串结束
            String urlR = url.substring(index + "/admin".length());
        //从request中获取请求方法
        String method = request.getMethod();
        //封装日志记录
        sysOperLog.setOperUser(username);
        sysOperLog.setOperUrl(urlR);
        sysOperLog.setJsonResult(JsonResult);
        sysOperLog.setRequestMethod(method);
        baseMapper.insert(sysOperLog);
    }
}
