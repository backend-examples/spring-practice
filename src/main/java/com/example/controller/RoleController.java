package com.example.controller;

import com.example.domain.Role;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/role")
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView) {

        List<Role> roleList = roleService.list();

        // 填充模型
        modelAndView.addObject("roleList", roleList);

        // 设置视图
        modelAndView.setViewName("role-list");

        return modelAndView;
    }

    @RequestMapping("/save")
    public String save(Role role) {
        roleService.save(role);

        // 必须先跳转到role/list这个接口去查数据，之后再由role/list接口转发到role-list这个页面
        // role-list才会有数据，如果直接前往，role-list没有数据
        return "redirect:/role/list";
    }
}
