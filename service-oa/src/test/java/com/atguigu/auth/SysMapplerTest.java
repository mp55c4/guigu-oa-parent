package com.atguigu.auth;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.model.system.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SysMapplerTest {
    @Autowired
    private SysRoleMapper mapper;
    @Test
    public void test(){
        List<SysRole> list = mapper.selectList(null);
        System.out.println(list);
    }
    @Test
    public void add(){
        SysRole sysrole = new SysRole();
        sysrole.setRoleName("角色管理员1");
        sysrole.setRoleCode("role");
        sysrole.setDescription("角色管理员1");
        int rows = mapper.insert(sysrole);
        System.out.println(rows);
        System.out.println(sysrole.getId());
    }
    @Test
    public void update(){
        //根据id查询
        SysRole role = mapper.selectById(10);
        //设置修改值
        role.setRoleName("我糙");
        //调用方法实现最终修改
        int rows = mapper.updateById(role);
        System.out.println(rows);
    }
    //删除操作软删除
    @Test
    public void deleteId(){
        mapper.deleteById(10);
    }
    //批量删除
    @Test
    public void deleteBatchIds(){
        int result = mapper.deleteBatchIds(Arrays.asList(1, 2));
        System.out.println(result);
    }
    //条件查询
    @Test
    public void testQuery1(){
        //创建QueryWrapper对象，调用方法封装条件
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name","角色管理员");
        //调用mp方法实现查询操作
        List<SysRole> list = mapper.selectList(wrapper);
        System.out.println(list);
    }
    //条件查询
    @Test
    public void testQuery2(){
        //创建Q
    }
}
