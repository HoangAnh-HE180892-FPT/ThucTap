package com.company.hoanganh_thuctap_project.view.step;

import com.company.hoanganh_thuctap_project.entity.Step;
import com.company.hoanganh_thuctap_project.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "steps/:id", layout = MainView.class)
@ViewController(id = "Step.detail")
@ViewDescriptor(path = "step-detail-view.xml")
@EditedEntityContainer("stepDc")
public class StepDetailView extends StandardDetailView<Step> {
}