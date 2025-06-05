package com.lazymind.java_in_readme_backend.db.blog.service;

import com.lazymind.java_in_readme_backend.db.blog.model.Blog;
import com.lazymind.java_in_readme_backend.db.blog.model.BlogPK;
import com.lazymind.java_in_readme_backend.db.blog.repository.BlogRepository;
import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public String saveAll(List<Blog> blogs){
        int errorCount = 0;

        for(Blog blog : blogs){
            final String error = save(blog);
            if(error != null) {
                log.error("Error at saving blog: {}",error);
                errorCount++;
            }
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

    public Blog findById(BlogPK blogPK){
        return blogRepository.findById(blogPK).orElse(null);
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
