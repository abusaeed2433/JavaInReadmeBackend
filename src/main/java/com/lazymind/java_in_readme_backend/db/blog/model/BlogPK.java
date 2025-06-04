package com.lazymind.java_in_readme_backend.db.blog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BlogPK implements Serializable {

    @Column(name = "topic_name")
    private String topicName;

    @Column(name = "sub_topic_name")
    private String subTopicName;
}
