package com.lazymind.java_in_readme_backend.db.sub_topic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SubTopicDTO {

    @JsonProperty("topic_name")
    private String topicName;

    @JsonProperty("sub_topic_name")
    private String subTopicName;
}
