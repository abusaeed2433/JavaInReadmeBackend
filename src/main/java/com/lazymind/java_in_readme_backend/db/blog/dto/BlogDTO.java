package com.lazymind.java_in_readme_backend.db.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BlogDTO {

    @JsonProperty("topic_name")
    private final String topicName;

    @JsonProperty("sub_topic_name")
    private final String subTopicName;

    @JsonProperty("content")
    private final String content;

}
