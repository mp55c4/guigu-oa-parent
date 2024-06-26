package com.atguigu.process.service.impl;

import com.atguigu.auth.service.SysUserService;
import com.atguigu.model.process.ProcessRecord;
import com.atguigu.model.system.SysUser;
import com.atguigu.process.mapper.OaProcessRecordMapper;
import com.atguigu.process.service.OaProcessRecordService;
import com.atguigu.security.custom.LoginUserInfoHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批记录 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2024-05-01
 */
@Service
public class ProcessRecordServiceImpl extends ServiceImpl<OaProcessRecordMapper, ProcessRecord> implements OaProcessRecordService {

    @Autowired
    private OaProcessRecordService processRecordService;
    @Autowired
    private OaProcessRecordMapper processRecordMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public void record(Long processId, Integer status, String description) {
        SysUser sysUser = sysUserService.getById(LoginUserInfoHelper.getUserId());
        ProcessRecord processRecord = new ProcessRecord();
        processRecord.setProcessId(processId);
        processRecord.setStatus(status);
        processRecord.setDescription(description);
        processRecord.setOperateUserId(sysUser.getId());
        processRecord.setOperateUser(sysUser.getName());
        processRecordMapper.insert(processRecord);
    }

}
