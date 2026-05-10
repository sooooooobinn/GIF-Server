package com.example.gif.project.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ProjectUpdateMemberRequest {
    private List<String> memberProviderIds;
}