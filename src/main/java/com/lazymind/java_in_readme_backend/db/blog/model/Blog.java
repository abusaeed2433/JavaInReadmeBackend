package com.lazymind.java_in_readme_backend.db.blog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopic;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
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
@Table(name = "blog")
public class Blog implements Serializable {
    @EmbeddedId
    private BlogPK blogPK;

    @MapsId // This maps the embedded ID to the SubTopic relationship
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "topic_name", referencedColumnName = "topic_name"),
            @JoinColumn(name = "sub_topic_name", referencedColumnName = "sub_topic_name")
    })
    @JsonProperty("subTopic")
    @Nonnull
    private SubTopic subTopic;

    @Nonnull
    @Column(columnDefinition = "MEDIUMTEXT", name = "content")
    private String content;

    public Blog(SubTopic subTopic, String content) {
        this.subTopic = subTopic;
        this.content = content;
        // Set the embedded ID from the SubTopic
        this.blogPK = new BlogPK(subTopic.getTopicName(), subTopic.getSubTopicName());
    }

    @Override
    public String toString() {
        return subTopic+": "+content.length() +" chars";
    }
}