package com.company.hoanganh_thuctap_project.security;

import com.company.hoanganh_thuctap_project.entity.Department;
import com.company.hoanganh_thuctap_project.entity.Step;
import com.company.hoanganh_thuctap_project.entity.User;
import com.company.hoanganh_thuctap_project.entity.UserStep;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "Hr Manager", code = HrManagerRole.CODE, scope = "UI")
public interface HrManagerRole {
    String CODE = "hr-manager";

    @MenuPolicy(menuIds = "User.list")
    @ViewPolicy(viewIds = "User.list")
    void screens();

    @EntityAttributePolicy(entityClass = Department.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Department.class, actions = EntityPolicyAction.READ)
    void department();

    @EntityAttributePolicy(entityClass = Step.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Step.class, actions = EntityPolicyAction.READ)
    void step();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void user();

    @EntityAttributePolicy(entityClass = UserStep.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = UserStep.class, actions = EntityPolicyAction.ALL)
    void userStep();
}