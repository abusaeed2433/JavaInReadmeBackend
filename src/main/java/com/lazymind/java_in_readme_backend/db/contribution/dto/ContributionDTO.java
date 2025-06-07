package com.lazymind.java_in_readme_backend.db.contribution.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContributionDTO {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("contribution_count")
    private Integer contributionCount;

    @JsonProperty("profile_url")
    private String profileUrl;

}
