package com.atguigu.oper.service;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysOperLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author atguigu
 * @since 2024-05-05
 */
public interface SysOperLogService extends IService<SysOperLog> {

    void saveOperLog(HttpServletRequest request, Result<Result> result) throws IOException;
}
