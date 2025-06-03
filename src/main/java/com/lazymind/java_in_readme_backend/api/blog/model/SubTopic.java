package com.lazymind.java_in_readme_backend.api.blog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subtopic")
@IdClass(SubTopicPK.class)
public class SubTopic implements Serializable {

    @Id
    @Column(name = "topic_name")
    private String topicName;

    @Id
    @Column(name = "sub_topic_name")
    private String subTopicName;

    @ManyToOne
    @JoinColumn(name = "topic_name", insertable = false, updatable = false)
    @JsonProperty("topic")
    private Topic topic;

}