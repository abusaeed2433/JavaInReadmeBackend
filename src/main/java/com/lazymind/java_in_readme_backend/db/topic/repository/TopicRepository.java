package com.lazymind.java_in_readme_backend.db.topic.repository;

import com.lazymind.java_in_readme_backend.db.topic.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {

}
