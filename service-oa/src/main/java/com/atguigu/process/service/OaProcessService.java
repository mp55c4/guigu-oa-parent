package com.atguigu.process.service;

import com.atguigu.vo.process.ApprovalVo;
import com.atguigu.vo.process.ProcessFormVo;
import com.atguigu.vo.process.ProcessQueryVo;
import com.atguigu.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.model.process.Process;

import java.util.Map;

/**
 * <p>
 * 审批类型 服务类
 * </p>
 *
 * @author atguigu
 * @since 2024-04-29
 */

public interface OaProcessService extends IService<Process> {

    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo);
    void deployByZip(String deployPath);

    void startUp(ProcessFormVo processFormVo);

    Object findPending(Page<java.lang.Process> pageParam);
    Map<String, Object> show(Long id);
    void approve(ApprovalVo approvalVo);
    IPage<ProcessVo> findProcessed(Page<Process> pageParam);
    IPage<ProcessVo> findStarted(Page<ProcessVo> pageParam);
}
