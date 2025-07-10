package com.company.hoanganh_thuctap_project.view.step;

import com.company.hoanganh_thuctap_project.entity.Step;
import com.company.hoanganh_thuctap_project.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "steps", layout = MainView.class)
@ViewController(id = "Step.list")
@ViewDescriptor(path = "step-list-view.xml")
@LookupComponent("stepsDataGrid")
@DialogMode(width = "64em")
public class StepListView extends StandardListView<Step> {
}