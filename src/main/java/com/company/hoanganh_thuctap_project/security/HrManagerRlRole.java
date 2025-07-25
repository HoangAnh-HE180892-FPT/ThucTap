package com.company.hoanganh_thuctap_project.security;

import com.company.hoanganh_thuctap_project.entity.Department;
import com.company.hoanganh_thuctap_project.entity.User;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "HR manager’s departments and users", code = HrManagerRlRole.CODE)
public interface HrManagerRlRole {
    String CODE = "hr-manager-rl";

    @JpqlRowLevelPolicy(entityClass = Department.class, where = "{E}.hrManager.id=:current_user_id")
    void department();

    @JpqlRowLevelPolicy(
            entityClass = User.class,
            where = "{E}.department.hrManager.id = :current_user_id")
    void user();

}