package com.lazymind.java_in_readme_backend.db.contribution.service;

import com.lazymind.java_in_readme_backend.db.contribution.model.Contribution;
import com.lazymind.java_in_readme_backend.db.contribution.repository.ContributionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContributionService {

    private final ContributionRepository repository;

    public String saveAll(List<Contribution> contributions){
        int errorCount = 0;

        for(Contribution item : contributions){
            final String error = save(item);
            if(error != null) errorCount++;
        }

        if(errorCount == 0) return null;

        return "Error at saving " + errorCount +" repo info";
    }

    public String save(Contribution item){
        try{
            repository.save(item);
            return null;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public List<Contribution> readSeriallyByRepoName(String repoName){
        return repository.findByRepoNameSortedByScore(repoName);
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
