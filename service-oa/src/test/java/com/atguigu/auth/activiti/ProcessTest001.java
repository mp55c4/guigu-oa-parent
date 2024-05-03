package com.atguigu.auth.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ProcessTest001 {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Test
    public void delplyProcess(){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/jiaban001.bpmn20.xml")
                .name("加班流程申请")
                .deploy();
        Map<String, Object> map = new HashMap<>();
        map.put("day", "1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("jiaban001",map);
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
        System.out.println(processInstance.getId());
    }
    @Test
    public void findGroupTaskList(){
        List<Task> TaskList = taskService.createTaskQuery()
                .taskAssignee("zhao6")
                .list();
        for(Task task : TaskList){
            System.out.println("----------------------------");
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }
    //拾取组任务
    @Test
    public void claimTask(){
        Task task = taskService.createTaskQuery()
                .taskCandidateUser("tom01")
                .singleResult();
        if(task!=null){
            taskService.claim(task.getId(),"tom01");
            System.out.println("拾取任务成功");
        }
    }
    //查询个人待办任务
    @Test
    public void findGroupPendingTaskList() {
        //任务负责人
        String assignee = "tom01";
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(assignee)//只查询该任务负责人的任务
                .list();
        for (Task task : list) {
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }
    //办理个人任务
    public void completTask() {
        Task task = taskService.createTaskQuery()
                .taskAssignee("tom01")
                .singleResult();
        taskService.complete(task.getId());
    }
    //归还组任务
    public void releaseTask() {
        Task task = taskService.createTaskQuery()
                .taskAssignee("tom01")
                .singleResult();
        taskService.setAssignee(task.getId(),null);
    }
}
