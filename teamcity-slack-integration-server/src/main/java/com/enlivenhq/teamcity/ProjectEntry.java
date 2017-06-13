package com.enlivenhq.teamcity;

import jetbrains.buildServer.responsibility.TestNameResponsibilityEntry;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.tests.TestName;

public class ProjectEntry {
    private String name;
    private String id = null;
    public ProjectEntry(SProject project) {
        name = project.getFullName();
        id = project.getExternalId();
    }

    public String getName() { return name; }
    public String getId() { return id; }
}
