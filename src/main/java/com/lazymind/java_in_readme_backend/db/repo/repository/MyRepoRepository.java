package com.lazymind.java_in_readme_backend.db.repo.repository;

import com.lazymind.java_in_readme_backend.db.repo.model.MyRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyRepoRepository extends JpaRepository<MyRepo, String> {

    @Query("SELECT mr from MyRepo mr ORDER BY mr.serial")
    List<MyRepo> readSerially();

}
