package com.lazymind.java_in_readme_backend.db.last_fetched.service;

import com.lazymind.java_in_readme_backend.db.last_fetched.model.LastFetched;
import com.lazymind.java_in_readme_backend.db.last_fetched.repository.LastFetchedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LastFetchedService {

    private final LastFetchedRepository repository;

    public LastFetched getLastOne(){
        return repository.findTopByOrderByTimestampDesc().orElse(null);
    }

    public String save(LastFetched item){
        try{
            repository.save(item);
            return null;
        }catch (Exception e){
            return e.getMessage();
        }
    }

}
