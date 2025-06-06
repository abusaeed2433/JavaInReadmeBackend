package com.lazymind.java_in_readme_backend.db.repo.service;

import com.lazymind.java_in_readme_backend.db.repo.model.MyRepo;
import com.lazymind.java_in_readme_backend.db.repo.repository.MyRepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyRepoService {

    private final MyRepoRepository repository;

    public String saveAll(List<MyRepo> myRepos){
        int errorCount = 0;

        for(MyRepo topic : myRepos){
            final String error = save(topic);
            if(error != null) errorCount++;
        }

        if(errorCount == 0) return null;

        return "Error at saving " + errorCount +" repo info";
    }

    public String save(MyRepo item){
        try{
            repository.save(item);
            return null;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public List<MyRepo> readSerially(){
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
