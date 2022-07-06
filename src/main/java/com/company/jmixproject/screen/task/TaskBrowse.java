package com.company.jmixproject.screen.task;

import io.jmix.ui.screen.*;
import com.company.jmixproject.entity.Task;

@UiController("Task_.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {
}