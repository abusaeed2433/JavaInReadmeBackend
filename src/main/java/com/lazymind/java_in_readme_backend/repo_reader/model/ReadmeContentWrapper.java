package com.lazymind.java_in_readme_backend.repo_reader.model;

import java.util.Map;

public record ReadmeContentWrapper(ReadmeContent readmeContent, Map<String, ReadmeContent> topicContentMap) {
}
