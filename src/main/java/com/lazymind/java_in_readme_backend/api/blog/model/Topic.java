package com.lazymind.java_in_readme_backend.api.blog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topic")
public class Topic implements Serializable {

    @Id
    @Column(name = "topic_name")
    @JsonProperty("topic_name")
    private String topicName;

    @Column(name = "no_of_sub_topics")
    @JsonProperty("no_of_sub_topics")
    private Integer noOfSubTopics;

    @Column(name = "folder_name")
    @JsonProperty("folder_name")
    private String folderName;

}
