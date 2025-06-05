package com.lazymind.java_in_readme_backend.db.topic.repository;

import com.lazymind.java_in_readme_backend.db.topic.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {

    @Query("SELECT t from Topic t ORDER BY t.serial")
    List<Topic> readSerially();

}
