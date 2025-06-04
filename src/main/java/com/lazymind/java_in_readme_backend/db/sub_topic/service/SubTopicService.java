package com.lazymind.java_in_readme_backend.db.sub_topic.service;

import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopic;
import com.lazymind.java_in_readme_backend.db.sub_topic.repository.SubTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubTopicService {

    private final SubTopicRepository repository;

    public String saveAll(List<SubTopic> subTopics){
        int errorCount = 0;

        for(SubTopic topic : subTopics){
            final String error = save(topic);
            if(error != null) errorCount++;
        }

        if(errorCount == 0) return null;

        return "Error at saving " + errorCount +" sub topics.";
    }

    public String save(SubTopic subTopic){
        try{
            repository.save(subTopic);
            return null;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String delete(SubTopic subTopic){
        try{
            repository.delete(subTopic);
            return null;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public List<SubTopic> readAll(){
        return repository.findAll();
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
