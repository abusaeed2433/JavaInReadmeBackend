package com.lazymind.java_in_readme_backend.repo_reader;

import com.lazymind.java_in_readme_backend.api.index.model.TopicWithSubTopic;
import com.lazymind.java_in_readme_backend.model.ReadmeContent;
import com.lazymind.java_in_readme_backend.model.ReadmeContentWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class DataGenerator {
    private static final String INDEX_KEY = "Topic Orders";

    private DataGenerator(){}

    public static final class InstanceHolder{
        private static final DataGenerator instance = new DataGenerator();
    }

    public static DataGenerator getInstance(){
        return InstanceHolder.instance;
    }

    public void generateAndSaveData(){
        List<TopicWithSubTopic> indices = generateIndex();
    }


    private List<TopicWithSubTopic> generateIndex(){
        final ReadmeContentWrapper wrapper = ReadmeExtractor.getInstance().extract("source/README.md");

        Map<String, ReadmeContent> map = wrapper.topicContentMap();
        if(map == null) return new ArrayList<>();

        final ReadmeContent readmeContent = map.getOrDefault(INDEX_KEY.toLowerCase(Locale.US), null);

        if(readmeContent == null) return new ArrayList<>();

        final String content = readmeContent.getContent().toString();

        if(content.isEmpty()) return new ArrayList<>();

        final String folderNames = content.substring( content.indexOf("```") + "```".length(), content.lastIndexOf("```") ).trim();

        final String[] folders = folderNames.replaceAll("[ ]","").split("[,]");

        log.info("All folders are: {}", Arrays.toString(folders));
        //List<TopicWithSubTopic> list = new ArrayList<>();
        return new ArrayList<>();
    }

}
