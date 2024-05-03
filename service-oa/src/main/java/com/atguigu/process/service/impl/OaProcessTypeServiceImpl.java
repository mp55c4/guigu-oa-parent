package com.atguigu.process.service.impl;

import com.atguigu.model.process.ProcessTemplate;
import com.atguigu.model.process.ProcessType;
import com.atguigu.process.mapper.OaProcessTypeMapper;
import com.atguigu.process.service.OaProcessTemplateService;
import com.atguigu.process.service.OaProcessTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 审批类型 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2024-04-28
 */
@Service
public class OaProcessTypeServiceImpl extends ServiceImpl<OaProcessTypeMapper, ProcessType> implements OaProcessTypeService {
    @Autowired
    private OaProcessTemplateService oaProcessTemplateService;
    @Override
    public List<ProcessType> findProcessType() {
        //1.查询所有审批分类，返回list集合
        List<ProcessType> processTypes = baseMapper.selectList(null);
        //2.遍历返回所有审批分类list集合
        for (ProcessType processType : processTypes) {
            //3.每个审批分类，查询对应审批模板，封装到每个审批分类里面
            Long typeId = processType.getId();
            LambdaQueryWrapper<ProcessTemplate> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProcessTemplate::getProcessTypeId,typeId);
            List<ProcessTemplate> processTemplateList = oaProcessTemplateService.list(wrapper);
            //应该使用迭代器来安全实现
//            Iterator<ProcessTemplate> iterator = processTemplateList.iterator();
//            while (iterator.hasNext()){
//                ProcessTemplate processTemplate = iterator.next();
//                if(processTemplate.getStatus() == 0){
//                    iterator.remove();
//                }
//            }
            //stream流的写法
//            processTemplateList = processTemplateList.stream()
//                    .filter(processTemplate -> processTemplate.getStatus() == 1)
//                    .collect(Collectors.toList());
//如果用remove方式实现会出现currentmodificationexception
            for(ProcessTemplate processTemplate : processTemplateList){
                if(processTemplate.getStatus() == 0){
                    processTemplateList.remove(processTemplate);
                }
            }
            //4.根据审批分类id查询对应审批模版数据list封装到每个审批分类对象里面
            processType.setProcessTemplateList(processTemplateList);
        }
        return processTypes;
    }
}
