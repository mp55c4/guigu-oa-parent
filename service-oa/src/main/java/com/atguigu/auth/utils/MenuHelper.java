package com.atguigu.auth.utils;

import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    //使用递归创建菜单
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList){
        List<SysMenu> trees = new ArrayList<>();
        for(SysMenu sysMenu:sysMenuList){
            //递归入口进入
            if(sysMenu.getParentId() == 0){
                trees.add(getChilder(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    private static SysMenu getChilder(SysMenu sysMenu,
                                      List<SysMenu> sysMenuList) {
        sysMenu.setChildren(new ArrayList<>());
        for(SysMenu it:sysMenuList){
            if(sysMenu.getId() == null){
                sysMenu.setChildren(new ArrayList<>());
            }
            if(sysMenu.getId() == it.getParentId()){
                sysMenu.getChildren().add(getChilder(it,sysMenuList));
            }
        }
        return sysMenu;
    }
}
