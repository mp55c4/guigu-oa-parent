package com.atguigu.auth.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProcessTest3 {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    //查询
    @Test
    public void findTaskList(){
        List<Task> TaskList = taskService.createTaskQuery()
                .taskAssignee("tom").list();
        for (Task task : TaskList) {
            System.out.println("流程实例id"+task.getProcessInstanceId());
            System.out.println("任务id"+task.getId());
            System.out.println("任务负责人"+task.getAssignee());
            System.out.println("任务名称"+task.getName());
        }
    }
    //模拟流程结束
    @Test
    public void completeTask(){
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("jack").list();
        Task task = taskList.get(1);
        taskService.complete(task.getId());

    }
    @Test
    public void findTaskList1(){
        List<Task> TaskList = taskService.createTaskQuery()
                .list();
        for (Task task : TaskList) {
            System.out.println("流程实例id"+task.getProcessInstanceId());
            System.out.println("流程名称"+task.getName());
            System.out.println("任务id"+task.getId());
            System.out.println("任务负责人"+task.getAssignee());
            System.out.println("任务名称"+task.getName());
        }
    }

    @Test
    public void deployProcess03() {
        // 流程部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/jiaban02.bpmn20.xml")
                .name("加班申请流程")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startUpProcess03() {
        //创建流程实例,我们需要知道流程定义的key
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("jiaban02");
        //输出实例的相关信息
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
    }
}
