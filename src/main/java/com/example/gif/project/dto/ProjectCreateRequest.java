package com.example.gif.project.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ProjectCreateRequest {

    private String projectName;
    private String teamName;
    private String description;
    private List<String> members;
    private String teamLogoUrl;

}
