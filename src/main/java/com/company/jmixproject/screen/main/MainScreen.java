package com.company.jmixproject.screen.main;

import com.company.jmixproject.app1.TaskService;
import com.company.jmixproject.entity.Project;
import com.company.jmixproject.entity.Task;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@UiController("MainScreen")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;
    @Autowired
    private CollectionLoader<Project> projectsDl;
    @Autowired
    private CollectionLoader<Task> tasksDl;
    @Autowired
    private TaskService taskService;
    @Autowired
    private EntityComboBox<Project> projectSelector;
    @Autowired
    private TextField nameSelector;
    @Autowired
    private DateField<LocalDateTime> dateSelector;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle();
        if (drawer.isCollapsed()) {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());
        screenTools.handleRedirect();
    }

    @Subscribe("addTaskAction")
    public void onAddTaskAction(Action.ActionPerformedEvent event) {
        if (projectSelector.getValue() == null
                || nameSelector.getValue() == null
                || dateSelector.getValue() == null) {
            notifications.create()
                    .withCaption(messageBundle.getMessage("validation.fieldsNotFilled.message"))
                    .withType(Notifications.NotificationType.WARNING)
                    .show();
            projectSelector.focus();
            return;
        }

        taskService.createTask(projectSelector.getValue(),(String) nameSelector.getValue(), dateSelector.getValue());
        tasksDl.load();
        projectSelector.setValue(null);
        nameSelector.setValue(null);
        dateSelector.setValue(null);

    }

    @Subscribe("refreshAction")
    public void onRefreshAction1(Action.ActionPerformedEvent event) {
        projectsDl.load();
        tasksDl.load();

    }

    @Subscribe("tasksCalendar")
    public void onTasksCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<Task> event) {
        Task task = (Task) event.getEntity();

        if (task == null){
            return;
        }

        Screen screen = screenBuilders.editor(Task.class, this)
                .editEntity(task)
                .withOpenMode(OpenMode.DIALOG)
                .build();

        screen.addAfterCloseListener(afterCloseEvent -> {
            if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)){
                tasksDl.load();
            }
        });

        screen.show();
    }

}
