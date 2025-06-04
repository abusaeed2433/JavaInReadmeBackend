package com.lazymind.java_in_readme_backend.db.blog.service;

import com.lazymind.java_in_readme_backend.db.blog.model.Blog;
import com.lazymind.java_in_readme_backend.db.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public String saveAll(List<Blog> blogs){
        int errorCount = 0;

        for(Blog blog : blogs){
            final String error = save(blog);
            if(error != null) errorCount++;
        }

        if(errorCount == 0) return null;

        return "Error at saving " + errorCount +" blogs.";
    }

    public String save(Blog blog){
        try{
            blogRepository.save(blog);
            return null;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String delete(Blog blog){
        try{
            blogRepository.delete(blog);
            return null;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public List<Blog> readAll(){
        return blogRepository.findAll();
    }

    public String deleteAll(){
        try {
            blogRepository.deleteAll();
            return null;
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

}
