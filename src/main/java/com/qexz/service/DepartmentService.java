package com.qexz.service;

import com.qexz.model.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService {

    int addDepartment(Department Department);

    boolean updateDepartment(Department Department);

    Department getDepartmentById(int id);

    Map<String, Object> getDepartments(int pageNum, int pageSize);

    List<Department> getDepartments();

    boolean deleteDepartmentById(int id);
}
