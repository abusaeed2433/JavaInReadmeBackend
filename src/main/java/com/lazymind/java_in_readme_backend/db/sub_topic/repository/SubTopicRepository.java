package com.lazymind.java_in_readme_backend.db.sub_topic.repository;

import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopic;
import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopicPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTopicRepository extends JpaRepository<SubTopic, SubTopicPK> {
}
