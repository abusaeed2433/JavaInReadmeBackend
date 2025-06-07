package com.lazymind.java_in_readme_backend.schedular;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazymind.java_in_readme_backend.db.blog.model.Blog;
import com.lazymind.java_in_readme_backend.db.blog.service.BlogService;
import com.lazymind.java_in_readme_backend.db.contribution.model.Contribution;
import com.lazymind.java_in_readme_backend.db.contribution.service.ContributionService;
import com.lazymind.java_in_readme_backend.db.last_fetched.model.LastFetched;
import com.lazymind.java_in_readme_backend.db.last_fetched.service.LastFetchedService;
import com.lazymind.java_in_readme_backend.db.repo.model.MyRepo;
import com.lazymind.java_in_readme_backend.db.repo.service.MyRepoService;
import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopic;
import com.lazymind.java_in_readme_backend.db.sub_topic.service.SubTopicService;
import com.lazymind.java_in_readme_backend.db.topic.model.Topic;
import com.lazymind.java_in_readme_backend.db.topic.service.TopicService;
import com.lazymind.java_in_readme_backend.repo_reader.RepoReader;
import com.lazymind.java_in_readme_backend.repo_reader.model.Pair;
import com.lazymind.java_in_readme_backend.storage.service.FileStorageService;
import com.lazymind.java_in_readme_backend.utility.DataGenerator;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.lazymind.java_in_readme_backend.repo_reader.RepoReader.REPO_OWNER;

@Slf4j
@Service
@RequiredArgsConstructor
public class PeriodicScheduler {

    // alert REPO_NAME will be replaced in string before using
    private static final String REPO_DETAILS_API = "https://api.github.com/repos/" +REPO_OWNER+ "/REPO_NAME";
    private static final String CONTRIBUTION_API = "https://api.github.com/repos/" +REPO_OWNER+ "/REPO_NAME/contributors";

    private static final List<String> REPO_NAMES = List.of( "JavaInREADME", "JavaInReadmeBackend", "java-in-readme-site" );

    @Value("${github.access_token:NOT_SET}")
    private String githubToken;

    private final ObjectMapper objectMapper;

    private final BlogService blogService;
    private final TopicService topicService;
    private final SubTopicService subTopicService;
    private final FileStorageService storageService;
    private final LastFetchedService lastFetchedService;

    private final MyRepoService myRepoService;
    private final ContributionService contributionService;

    private final WebClient webClient;

    /**
     * Runs every day at 8:32PM and does the following
     * Update Repo contributors
     * Update blogs with indices
     */
//    @Scheduled(cron = "0 */1 * * * ?") // at every 5 minutes
    @Scheduled(cron = "0 32 20 * * ?") // at 8:32PM every day
    public void processRepo(){

        log.info("Started processRepo function at: {}", LocalDateTime.now());
        updateRepoAndContributors();
        log.info("Contributor processed");

        updateCodeBase();
        log.info("Ended processRepo function");
    }

    /**
     * Update the codebase using git
     * Extract content(topics, subtopics, blogs) from data
     * Backup current table data as json
     * Delete current table data
     * Insert newly extracted data
     */
    private void updateCodeBase(){
        log.info("Updating codebase");
        boolean isCodebaseUpdated = RepoReader.getInstance().updateCodebase(githubToken);
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
    }

    private void updateRepoAndContributors(){

        final Pair< List<MyRepo>, List<Contribution> > repoContrib = getDataFromAPI();

        final List<MyRepo> repoList = repoContrib.getFirst();
        final List<Contribution> contributionList = repoContrib.getSecond();

        if(repoList.isEmpty()){
            log.debug("No repo found at updateRepoAndContributors");
            return;
        }

        if( contributionList.isEmpty() ){
            log.debug("No contributor found: updateRepoAndContributors");
            return;
        }

        log.info("Deleting all from my repo table");
        myRepoService.deleteAll();

        log.info("Saving all to my repo table");
        myRepoService.saveAll(repoList);

        log.info("Deleting all from contribution table");
        contributionService.deleteAll();

        log.info("Saving all from contribution table");
        contributionService.saveAll(contributionList);
    }

    @NonNull
    private Pair< List<MyRepo>, List<Contribution> > getDataFromAPI(){

        final List<MyRepo> myRepoList = new ArrayList<>();
        final List<Contribution> contributionList = new ArrayList<>();

        for(int i=0; i<REPO_NAMES.size(); i++){
            final String repoName = REPO_NAMES.get(i);

            final String detailsAPI = REPO_DETAILS_API.replace("REPO_NAME", repoName);
            final String contribAPI = CONTRIBUTION_API.replace("REPO_NAME", repoName);

            final String detailsJson = webClient.get()
                    .uri(detailsAPI)
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            final MyRepo myRepo = extractMyRepoFromJson(detailsJson, i);
            if(myRepo == null) continue;

            final String contribJson = webClient.get()
                    .uri(contribAPI)
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            final List<Contribution> contributions = extractContribFromJson(contribJson, repoName);

            myRepoList.add( myRepo );
            contributionList.addAll( contributions );
        }
        return new Pair<>( myRepoList, contributionList );
    }

    private MyRepo extractMyRepoFromJson(String details, int serial){
        try {
            final JsonNode jsonNode = objectMapper.readTree(details);

            final String repoName = getSafeText( jsonNode, "name");
            final String desc = getSafeText( jsonNode, "description");
            final String url = getSafeText( jsonNode, "html_url");

            return new MyRepo(repoName, desc, url, serial);
        } catch (JsonProcessingException e) {
            log.debug("Failed to parse repository JSON", e);
            return null;
        }
    }

    @NonNull
    private List<Contribution> extractContribFromJson(String contrib, String repoName){

        try {
            final JsonNode jsonNode = objectMapper.readTree(contrib);

            if( !jsonNode.isArray() ){
                log.debug("Invalid format from contribution api");
                return new ArrayList<>();
            }

            final List<Contribution> contributionList = new ArrayList<>();

            for(JsonNode contribNode : jsonNode){
                final String strContrib = getSafeText(contribNode, "contributions");

                final Contribution contribution = new Contribution(
                        getSafeText(contribNode, "login"),// user name
                        repoName,
                        Integer.parseInt( (strContrib == null) ? "0" : strContrib ),
                        getSafeText(contribNode,"avatar_url")
                );
                contributionList.add( contribution );
            }

            return contributionList;
        }
        catch (JsonProcessingException e) {
            log.debug("Failed to parse repository JSON", e);
            return new ArrayList<>();
        }
    }

    @Nullable
    private String getSafeText(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asText() : null;
    }

}
