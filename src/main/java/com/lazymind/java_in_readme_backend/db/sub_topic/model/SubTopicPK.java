package com.lazymind.java_in_readme_backend.db.sub_topic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubTopicPK implements Serializable {

    private String topicName;
    private String subTopicName;

    @Override
    public int hashCode() {
        return Objects.hash(topicName, subTopicName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof SubTopicPK item)) return false;
        return Objects.equals(topicName, item.topicName) &&
                Objects.equals(subTopicName, item.subTopicName);
    }
}
