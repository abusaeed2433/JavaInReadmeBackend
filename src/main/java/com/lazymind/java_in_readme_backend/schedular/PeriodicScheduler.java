package com.lazymind.java_in_readme_backend.schedular;

import com.lazymind.java_in_readme_backend.db.blog.model.Blog;
import com.lazymind.java_in_readme_backend.db.blog.service.BlogService;
import com.lazymind.java_in_readme_backend.db.last_fetched.model.LastFetched;
import com.lazymind.java_in_readme_backend.db.last_fetched.service.LastFetchedService;
import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopic;
import com.lazymind.java_in_readme_backend.db.sub_topic.service.SubTopicService;
import com.lazymind.java_in_readme_backend.db.topic.model.Topic;
import com.lazymind.java_in_readme_backend.db.topic.service.TopicService;
import com.lazymind.java_in_readme_backend.repo_reader.RepoReader;
import com.lazymind.java_in_readme_backend.repo_reader.model.Pair;
import com.lazymind.java_in_readme_backend.storage.service.FileStorageService;
import com.lazymind.java_in_readme_backend.utility.DataGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeriodicScheduler {

    private final BlogService blogService;
    private final TopicService topicService;
    private final SubTopicService subTopicService;
    private final FileStorageService storageService;
    private final LastFetchedService lastFetchedService;

    /**
     * Runs every day at 8:32PM and does the following
     * Update the codebase using git
     * Extract content(topics, subtopics, blogs) from data
     * Backup current table data as json
     * Delete current table data
     * Insert newly extracted data
     */
//    @Scheduled(cron = "0 */1 * * * ?") // at every 5 minutes
    @Scheduled(cron = "0 32 20 * * ?") // at 8:32PM every day
    public void processRepo(){
        log.info("Started processRepo function at: {}", LocalDateTime.now());

        log.info("Updating codebase");
        boolean isCodebaseUpdated = RepoReader.getInstance().updateCodebase();
        log.info("Code base updated: {}", isCodebaseUpdated);

        final Pair<Pair<List<Topic>, List<SubTopic>>, List<Blog>> topicsSubTopicsBlogs = DataGenerator.getInstance().generateData();
        if(topicsSubTopicsBlogs == null){
            log.info("Failed to generate data");
            return;
        }

        log.info("Backing up current table data");
        final List<Topic> topics = topicsSubTopicsBlogs.getFirst().getFirst();
        final List<SubTopic> subTopics = topicsSubTopicsBlogs.getFirst().getSecond();
        final List<Blog> blogs = topicsSubTopicsBlogs.getSecond();

        {
            String error = storageService.saveToFiles(topics, subTopics, blogs);
            if(error != null){
                log.debug("Failed to save to files with: {}",error);
                log.info("Exiting operation....");
                return;
            }
            log.info("Backup completed");
        }

        log.info("Deleting old data");
        {
            String error = blogService.deleteAll();
            if(error != null){
                log.debug("Failed to delete from blog service with: {}", error);
            }
        }
        {
            String error = subTopicService.deleteAll();
            if(error != null){
                log.debug("Failed to delete from sub topic service with: {}", error);
            }
        }
        {
            String error = topicService.deleteAll();
            if(error != null){
                log.debug("Failed to delete from topic service with: {}", error);
            }
        }

        log.info("Deletion completed");

        log.info("Saving new data");
        {
            String error = topicService.saveAll(topics);
            if(error != null){
                log.debug("Failed to save all topics with: {}", error);
            }

        }
        {
            String error = subTopicService.saveAll(subTopics);
            if(error != null){
                log.debug("Failed to save all sub topics with: {}", error);
            }
        }
        {
            String error = blogService.saveAll(blogs);
            if(error != null){
                log.debug("Failed to save all blogs with: {}",error);
            }
        }
        log.info("Saved all data");

        final long timestamp = System.currentTimeMillis();
        final LastFetched lastFetched = LastFetched.builder()
                .timestamp(timestamp)
                .formattedTimestamp(LocalDateTime.now(ZoneId.of("Asia/Dhaka")).toString())
                .build();

        lastFetchedService.save(lastFetched);
        log.info("Ended processRepo function");
    }

}
