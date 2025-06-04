package com.lazymind.java_in_readme_backend.repo_reader.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public final class ReadmeContent {
    private final int level;
    private final String title;
    private final StringBuilder content = new StringBuilder();
    private final List<ReadmeContent> nestedContents = new ArrayList<>();

    public void addContent(ReadmeContent content){
        nestedContents.add(content);
    }

    @Override
    public String toString() {
        return "title: "+title+" with size: "+(nestedContents.size());
    }
}
