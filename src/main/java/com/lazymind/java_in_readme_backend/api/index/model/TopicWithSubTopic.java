package com.lazymind.java_in_readme_backend.api.index.model;

import com.lazymind.java_in_readme_backend.api.blog.model.SubTopic;
import com.lazymind.java_in_readme_backend.api.blog.model.Topic;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class TopicWithSubTopic {
    private final Topic topic;
    private List<SubTopic> subTopicList = new ArrayList<>();
}
