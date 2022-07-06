package com.company.jmixproject.security;

import com.company.jmixproject.entity.Project;
import com.company.jmixproject.entity.Task;
import com.company.jmixproject.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "ManagerRole", code = "manager-role")
public interface ManagerRole {

    @EntityAttributePolicy(entityClass = User.class, attributes = {"firstName", "lastName", "email", "address"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = User.class, attributes = {"id", "version", "password", "active", "timeZoneId", "username"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void user();

    @EntityAttributePolicy(entityClass = Project.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Project.class, actions = EntityPolicyAction.ALL)
    void project();

    @EntityAttributePolicy(entityClass = Task.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Task.class, actions = EntityPolicyAction.ALL)
    void task();

    @MenuPolicy(menuIds = {"User.browse", "Project.browse", "Task_.browse", "themeSettingsScreen"})
    @ScreenPolicy(screenIds = {"User.browse", "Project.browse", "Task_.browse", "Project.edit", "Task_.edit", "User.edit", "ui_FilterConfigurationModel.fragment", "ui_UiDataFilterConfigurationModel.fragment", "themeSettingsScreen", "MainScreen"})
    void screens();
}