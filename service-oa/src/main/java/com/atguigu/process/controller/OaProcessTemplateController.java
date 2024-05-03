package com.atguigu.process.controller;


import com.atguigu.common.result.Result;
import com.atguigu.model.process.ProcessTemplate;
import com.atguigu.process.service.OaProcessTemplateService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 审批模板 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2024-04-28
 */
@Api("审批管理")
@RestController
@RequestMapping("/admin/process/processTemplate")
@CrossOrigin
public class OaProcessTemplateController {
    @Autowired
    private OaProcessTemplateService oaprocessTemplateService;
    @ApiOperation("分页查询")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit){
        Page<ProcessTemplate> processTemplatePage = new Page<>(page, limit);
        IPage<ProcessTemplate> pageModel = oaprocessTemplateService.selectPage(processTemplatePage);
        return Result.ok(pageModel);
    }
    //@PreAuthorize("hasAuthority('bnt.processTemplate.list')")
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        ProcessTemplate processTemplate = oaprocessTemplateService.getById(id);
        return Result.ok(processTemplate);
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody ProcessTemplate processTemplate) {
        oaprocessTemplateService.save(processTemplate);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody ProcessTemplate processTemplate) {
        oaprocessTemplateService.updateById(processTemplate);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.remove')")
    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        oaprocessTemplateService.removeById(id);
        return Result.ok();
    }
    @ApiOperation("上传流程定义")
    @PostMapping("/uploadProcessDefinition")
    public Result uploadProcessDefinition(MultipartFile file) throws FileNotFoundException {
        //获取绝对路径
        String path = new File(ResourceUtils.getURL("classpath:")
                .getPath()).getAbsolutePath();
        //设置上传文件夹
        File tempFile = new File(path + "/processes/");
        if(!tempFile.exists()){
            tempFile.mkdirs();
        }
        String filename = file.getOriginalFilename();
        //创建空文件，实现文件写入
        File zipfile = new File(path + "/processes/" + filename);
        //保存文件
        try {
            file.transferTo(zipfile);
        } catch (IOException e) {
            return Result.fail();
        }
        Map<String,Object> map = new HashMap<>();
        //根据上传地址后续部署流程定义，文件名称为流程定义的默认key
        map.put("processDefinitionPath","processes/"+filename);
        map.put("processDefinitionKey",filename.substring(0,filename.lastIndexOf(".")));
        return Result.ok(map);
    }
    //部署流程定义（发布）
    @ApiOperation("发布")
    @GetMapping("publish/{id}")
    public Result publish(@PathVariable Long id){
        //修改模版发布状态1
        oaprocessTemplateService.publish(id);
        return Result.ok();
    }
}

