package com.lazymind.java_in_readme_backend.index.model;

import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopic;
import com.lazymind.java_in_readme_backend.db.topic.model.Topic;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class TopicWithSubTopic {
    private final Topic topic;
    private final List<SubTopic> subTopicList;

    @Override
    public String toString() {
        return topic+": "+subTopicList.size()+" sub topics";
    }
}
