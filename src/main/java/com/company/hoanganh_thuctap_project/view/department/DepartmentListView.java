package com.company.hoanganh_thuctap_project.view.department;

import com.company.hoanganh_thuctap_project.entity.Department;
import com.company.hoanganh_thuctap_project.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "departments", layout = MainView.class)
@ViewController(id = "Department.list")
@ViewDescriptor(path = "department-list-view.xml")
@LookupComponent("departmentsDataGrid")
@DialogMode(width = "64em")
public class DepartmentListView extends StandardListView<Department> {
}