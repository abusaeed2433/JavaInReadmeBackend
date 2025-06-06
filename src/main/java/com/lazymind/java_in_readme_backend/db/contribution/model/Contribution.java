package com.lazymind.java_in_readme_backend.db.contribution.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lazymind.java_in_readme_backend.db.repo.model.MyRepo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contribution_table")
public class Contribution {

    @EmbeddedId
    private ContributionPK contributionPK;

    @Column(name = "contribution_count")
    @JsonProperty("contribution_count")
    private Integer contributionCount;

    @Column(name = "profile_url")
    @JsonProperty("profile_url")
    private String profileUrl;

    // Relationship to MyRepo entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repo_name", referencedColumnName = "name",
            insertable = false, updatable = false)
    @JsonProperty("repo")
    private MyRepo repo;

    // Convenience constructor
    public Contribution(String userName, String repoName, Integer contributionCount, String profileUrl) {
        this.contributionPK = new ContributionPK(userName, repoName);
        this.contributionCount = contributionCount;
        this.profileUrl = profileUrl;
    }

    // Convenience getters for composite key fields
    @JsonProperty("user_name")
    public String getUserName() {
        return contributionPK != null ? contributionPK.getUserName() : null;
    }

    @JsonProperty("repo_name")
    public String getRepoName() {
        return contributionPK != null ? contributionPK.getRepoName() : null;
    }

    // Convenience setters for composite key fields
    public void setUserName(String userName) {
        if (this.contributionPK == null) {
            this.contributionPK = new ContributionPK();
        }
        this.contributionPK.setUserName(userName);
    }

    public void setRepoName(String repoName) {
        if (this.contributionPK == null) {
            this.contributionPK = new ContributionPK();
        }
        this.contributionPK.setRepoName(repoName);
    }
}