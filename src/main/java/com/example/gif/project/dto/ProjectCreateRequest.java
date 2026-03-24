package com.example.gif.project.dto;

import java.util.List;

public class ProjectCreateRequest {

    private String projectName;
    private String teamName;
    private String description;
    private List<String> members;

    public String getProjectName() {
        return projectName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getMembers() {
        return members;
    }
}
