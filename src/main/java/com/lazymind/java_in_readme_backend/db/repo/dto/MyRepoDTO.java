package com.lazymind.java_in_readme_backend.db.repo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lazymind.java_in_readme_backend.db.contribution.dto.ContributionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MyRepoDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("url")
    private String url;

    @JsonProperty("contribution")
    private List<ContributionDTO> contributions;

}
