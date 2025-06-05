package com.lazymind.java_in_readme_backend.db.topic.service;

import com.lazymind.java_in_readme_backend.db.topic.model.Topic;
import com.lazymind.java_in_readme_backend.db.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TopicService {

    private final TopicRepository repository;

    public String saveAll(List<Topic> topics){
        int errorCount = 0;

        for(Topic topic : topics){
            final String error = save(topic);
            if(error != null) errorCount++;
        }

        if(errorCount == 0) return null;

        return "Error at saving " + errorCount +"topics.";
    }

    public String save(Topic topic){
        try{
            repository.save(topic);
            return null;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String delete(Topic topic){
        try{
            repository.delete(topic);
            return null;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public List<Topic> readSerially(){
        return repository.readSerially();
    }

    public String deleteAll(){
        try {
            repository.deleteAll();
            return null;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

}
