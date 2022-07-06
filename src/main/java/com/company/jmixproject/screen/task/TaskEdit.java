package com.company.jmixproject.screen.task;

import io.jmix.ui.screen.*;
import com.company.jmixproject.entity.Task;

@UiController("Task_.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
public class TaskEdit extends StandardEditor<Task> {
}