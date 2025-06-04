package com.lazymind.java_in_readme_backend.db.blog.repository;

import com.lazymind.java_in_readme_backend.db.blog.model.Blog;
import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, SubTopic> {

}
