package com.qexz.dao;

import com.qexz.model.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface DepartmentMapper {

    int insertDepartment(@Param("department") Department department);

    int updateDepartmentById(@Param("department") Department department);

    Department getDepartmentById(@Param("id") int id);

    int getCount();

    List<Department> getDepartments();

    int deleteDepartmentById(@Param("id") int id);
}
