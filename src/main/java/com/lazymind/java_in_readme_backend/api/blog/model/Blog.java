package com.lazymind.java_in_readme_backend.api.blog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Id
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

}