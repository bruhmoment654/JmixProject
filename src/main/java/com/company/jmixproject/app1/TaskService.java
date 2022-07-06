package com.company.jmixproject.app1;

import com.company.jmixproject.entity.Project;
import com.company.jmixproject.entity.Task;
import com.company.jmixproject.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private CurrentAuthentication currentAuthentication;

    public void createTask(Project project, String name, LocalDateTime startDate) {
        Task task = dataManager.create(Task.class);
        task.setProject(project);
        task.setName(name);
        task.setStartDate(startDate);
        task.setAssignee((User) currentAuthentication.getUser());
        dataManager.save(task);


    }
}
