package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.model.Department;
import com.qexz.service.DepartmentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/department")
public class DepartmentController {

    private static Log LOG = LogFactory.getLog(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    //添加职位
    @RequestMapping(value="/api/addDepartment", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addDepartment(@RequestBody Department department) {
        AjaxResult ajaxResult = new AjaxResult();
        int departmentId = departmentService.addDepartment(department);
        return new AjaxResult().setData(departmentId);
    }

    //更新职位
    @RequestMapping(value="/api/updateDepartment", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateDepartment(@RequestBody Department department) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = departmentService.updateDepartment(department);
        return new AjaxResult().setData(result);
    }

    //删除职位
    @DeleteMapping("/api/deleteDepartment/{id}")
    public AjaxResult deleteDepartment(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = departmentService.deleteDepartmentById(id);
        return new AjaxResult().setData(result);
    }

    /**
     * 分页获取所有职位列表
     */
    @RequestMapping(value = "/api/getDepartments", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getDepartments(HttpServletRequest request, HttpServletResponse response) {
        AjaxResult ajaxResult = new AjaxResult();
        return ajaxResult;
    }
}
