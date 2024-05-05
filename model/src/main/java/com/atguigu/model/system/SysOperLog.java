package com.atguigu.model.system;

import com.atguigu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(description = "SysOperLog")
@TableName("sys_oper_log")
public class SysOperLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "请求方式")
	@TableField("request_method")
	private String requestMethod;

	@ApiModelProperty(value = "操作人员")
	@TableField("oper_user")
	private String operUser;

	@ApiModelProperty(value = "请求URL")
	@TableField("oper_url")
	private String operUrl;

	@ApiModelProperty(value = "返回参数")
	@TableField("json_result")
	private String jsonResult;

	@ApiModelProperty(value = "删除状态")
	@TableField("is_deleted")
	private Integer isDeleted;
}