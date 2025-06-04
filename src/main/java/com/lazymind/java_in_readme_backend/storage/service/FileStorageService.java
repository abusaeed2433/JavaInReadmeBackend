package com.lazymind.java_in_readme_backend.storage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazymind.java_in_readme_backend.db.blog.model.Blog;
import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopic;
import com.lazymind.java_in_readme_backend.db.topic.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@Service
public class FileStorageService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String OUTPUT_FOLDER = "backup";

    public String saveToFiles(List<Topic> topics, List<SubTopic> subTopics, List<Blog> blogs){
        try {
            final long timestamp = System.currentTimeMillis();

            final File folder = new File(OUTPUT_FOLDER);
            if(!folder.exists()){
                Files.createDirectories(folder.toPath());
            }

            saveTopics(topics, OUTPUT_FOLDER+"/topic_"+timestamp+".json");
            saveSubTopics(subTopics, OUTPUT_FOLDER+"/sub_topic_"+timestamp+".json");
            saveBlogs(blogs, OUTPUT_FOLDER+"/blog_"+timestamp+".json");
            return null;
        }catch (IOException e){
            log.debug("Failed to save topics: {}", e.getMessage());
            return e.getMessage();
        }
    }

    private void saveTopics(List<Topic> topics, String fullPath) throws IOException{
        File file = new File(fullPath);
        objectMapper.writeValue(file, topics);
    }
    private void saveSubTopics(List<SubTopic> subTopics, String fullPath) throws IOException{
        File file = new File(fullPath);
        objectMapper.writeValue(file, subTopics);
    }
    private void saveBlogs(List<Blog> blogs, String fullPath) throws IOException{
        File file = new File(fullPath);
        objectMapper.writeValue(file, blogs);
    }

}
