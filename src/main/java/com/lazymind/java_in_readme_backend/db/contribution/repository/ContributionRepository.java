package com.lazymind.java_in_readme_backend.db.contribution.repository;

import com.lazymind.java_in_readme_backend.db.contribution.model.Contribution;
import com.lazymind.java_in_readme_backend.db.contribution.model.ContributionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, ContributionPK> {

    @Query("SELECT c FROM Contribution c WHERE c.id.repoName = :repoName ORDER BY c.contributionCount DESC")
    List<Contribution> findByRepoNameSortedByScore(@Param("repoName") String repoName);

}
