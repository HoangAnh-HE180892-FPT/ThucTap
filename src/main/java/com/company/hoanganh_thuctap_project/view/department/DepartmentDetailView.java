package com.company.hoanganh_thuctap_project.view.department;

import com.company.hoanganh_thuctap_project.entity.Department;
import com.company.hoanganh_thuctap_project.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "departments/:id", layout = MainView.class)
@ViewController(id = "Department.detail")
@ViewDescriptor(path = "department-detail-view.xml")
@EditedEntityContainer("departmentDc")
public class DepartmentDetailView extends StandardDetailView<Department> {

}