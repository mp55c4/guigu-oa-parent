package com.atguigu.process.controller;


import com.atguigu.common.result.Result;
import com.atguigu.model.process.ProcessType;
import com.atguigu.process.service.OaProcessTypeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 审批类型 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2024-04-28
 */
@Api(value = "审批类型", tags = "审批类型")

@RestController
@RequestMapping("/admin/process/processType")
public class OaProcessTypeController {
    @Autowired
    private OaProcessTypeService oaProcessTypeService;
    @ApiOperation(value = "获取全部审批分类")
    @GetMapping("findAll")
    public Result findAll() {
        return Result.ok(oaProcessTypeService.list());
    }

    @ApiOperation("获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit){
        Page<ProcessType> pageParam = new Page<>(page, limit);
        IPage<ProcessType> pageModel = oaProcessTypeService.page(pageParam);
        return Result.ok(pageModel);
    }
    @ApiOperation("获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        ProcessType byId = oaProcessTypeService.getById(id);
        return Result.ok(byId);
    }
    @ApiOperation("新增")
    @PostMapping("save")
    public Result save(@RequestBody ProcessType processType){
        oaProcessTypeService.save(processType);
        return Result.ok(null);
    }
    @ApiOperation("修改")
    @PutMapping("update")
    public Result updateById(@RequestBody ProcessType processType){
        oaProcessTypeService.updateById(processType);
        return Result.ok(null);
    }
    @ApiOperation("删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        oaProcessTypeService.removeById(id);
        return Result.ok(null);
    }

}

