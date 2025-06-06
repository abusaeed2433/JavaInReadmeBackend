package com.lazymind.java_in_readme_backend.db.contribution.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContributionPK implements Serializable {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "repo_name")
    private String repoName;

    @Override
    public boolean equals(Object item) {
        if (this == item) return true;
        if (item == null || getClass() != item.getClass()) return false;
        ContributionPK that = (ContributionPK) item;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(repoName, that.repoName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, repoName);
    }
}
