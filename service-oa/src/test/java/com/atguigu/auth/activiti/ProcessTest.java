package com.atguigu.auth.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProcessTest {
    //植入RepositoryService
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    //全部流程挂起
    @Test
    public void suspendProcessDefinition(){
        //获取流程定义对象
        ProcessDefinition qingjia = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("qingjia").singleResult();
        //调用流程对象的方法判断当前状态，挂起、激活
        boolean suspended = qingjia.isSuspended();
        //如果挂起，那就激活
        if (suspended == true){
            repositoryService.activateProcessDefinitionById(qingjia.getId(),true,null);
            System.out.println(qingjia.getId()+"激活");
        }
        //如果激活，那就挂起
        else{
            repositoryService.suspendProcessDefinitionById(qingjia.getId(), true, null);
            System.out.println(qingjia.getId()+"挂起");
        }
    }
    //全部流程激活
    //创建流程实例
    @Test
    public void startUpProcessAddBusinessKey(){
        ProcessInstance instance =
                runtimeService.startProcessInstanceByKey("qingjia", "1001");
        System.out.println("流程实例id"+instance.getBusinessKey());
    }
    //查询已处理任务
    @Test
    public void findHistoryTaskList(){
        String assignee = "张三";
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .taskAssignee(assignee)
                .finished().list();
        for (HistoricActivityInstance historicActivityInstance : list){
            System.out.println("流程实例id"+historicActivityInstance.getProcessInstanceId());
            System.out.println("任务id"+historicActivityInstance.getId());
            System.out.println("任务名称"+historicActivityInstance.getActivityName());
            System.out.println("任务创建时间"+historicActivityInstance.getStartTime());
            System.out.println("任务办理人"+historicActivityInstance.getAssignee());
        }
    }
//    @Test
//    public void completTask(){
//        //查询负责人需要处理任务，返回一条
//        Task task = taskService.createTaskQuery()
//                .taskAssignee("张三")
//                .singleResult();
//        //完成任务
//        taskService.complete(task.getId());
//    }
    //查询某个人的待办任务
    @Test
    public void findTaskList(){
        String assignee = "张三";
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(assignee)
                .list();
        for (Task task : list){
            System.out.println("流程实例id"+task.getProcessInstanceId());
            System.out.println("任务id"+task.getId());
            System.out.println("任务名称"+task.getName());
            System.out.println("任务创建时间"+task.getCreateTime());
            System.out.println("任务办理人"+task.getAssignee());
            System.out.println("流程定义的id"+task.getProcessDefinitionId());
        }
                }

    //启动流程实例
    @Test
    public void startProcess(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qingjia");
        System.out.println("流程定义的id"+processInstance.getProcessDefinitionId());
        System.out.println("流程实例id"+processInstance.getId());
        System.out.println("流程活动id"+processInstance.getActivityId());

    }
    //部署流程qingjia
    @Test
    public void deployProcess(){
        //部署流程
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/qingjia.bpmn20.xml")
                .addClasspathResource("process/qingjia.png")
                .name("请假流程")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());

    }
}
