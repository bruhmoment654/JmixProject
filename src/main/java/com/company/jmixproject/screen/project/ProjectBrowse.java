package com.company.jmixproject.screen.project;

import com.company.jmixproject.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.screen.*;
import com.company.jmixproject.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@UiController("Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {

}