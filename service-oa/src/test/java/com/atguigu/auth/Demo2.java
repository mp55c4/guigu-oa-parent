package com.atguigu.auth;

import com.atguigu.auth.service.SysRoleService;
import com.atguigu.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
@SpringBootTest
public class Demo2 {
    @Autowired
    private SysRoleService service;
    @Test
    public void getAll(){
        List<SysRole> list = service.list();
        System.out.println(list);
    }
}
