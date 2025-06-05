package com.lazymind.java_in_readme_backend.db.topic.model;

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
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topic")
public class Topic implements Serializable, Comparable<Topic> {

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

    @Column(name = "serial")
    @JsonProperty("serial")
    private Integer serial = 0;

    @Override
    public String toString() {
        return topicName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicName);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if( !(obj instanceof Topic topic) ) return false;

        return Objects.equals(topicName, topic.topicName) &&
                Objects.equals(folderName, topic.folderName) &&
                Objects.equals(noOfSubTopics, topic.noOfSubTopics);
    }

    @Override
    public int compareTo(Topic item) {
        return topicName.compareTo(item.topicName);
    }
}
