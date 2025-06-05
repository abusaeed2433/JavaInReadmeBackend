package com.lazymind.java_in_readme_backend.utility;

import static com.lazymind.java_in_readme_backend.repo_reader.RepoReader.REPO_FOLDER;

import com.lazymind.java_in_readme_backend.db.blog.model.Blog;
import com.lazymind.java_in_readme_backend.db.sub_topic.model.SubTopic;
import com.lazymind.java_in_readme_backend.db.topic.model.Topic;
import com.lazymind.java_in_readme_backend.repo_reader.model.Pair;
import com.lazymind.java_in_readme_backend.repo_reader.model.ReadmeContent;
import com.lazymind.java_in_readme_backend.repo_reader.model.ReadmeContentWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DataGenerator {
    private static final String INDEX_KEY = "Topic Orders";

    public static final Map<String,String> folderValueMap = new TreeMap<>();
    static {
        folderValueMap.put("datatype", "Data type");
        folderValueMap.put("operator", "Operator");
        folderValueMap.put("classesandobject", "Class & Object");

        folderValueMap.put("theobjectclass", "The Object class");
        folderValueMap.put("wrapper_class", "Wrapper class");
        folderValueMap.put("exceptionhandling", "Exception Handling");

        folderValueMap.put("assertion", "Assertion");
        folderValueMap.put("string", "String");
        folderValueMap.put("datetime", "Date-Time");

        folderValueMap.put("formatter", "Basic formatting");
        folderValueMap.put("regex", "Regex");
        folderValueMap.put("array", "Array");

        folderValueMap.put("inheritance", "Inheritance");
        folderValueMap.put("interfaces", "Interface");
        folderValueMap.put("enum", "Enum");

        folderValueMap.put("java17", "What's New in java17");
        folderValueMap.put("qna", "Common Questions");
    }

    private static final class InstanceHolder{
        private static final DataGenerator instance = new DataGenerator();
    }

    public static DataGenerator getInstance(){
        return InstanceHolder.instance;
    }

    /**
     * Read from local already downloaded repo, and extract topics, subtopics, blogs.
     * @return Return Pair( Pair(topics, subtopics), blogs)
     */
    public Pair<Pair<List<Topic>,List<SubTopic>>, List<Blog>> generateData(){
        final ReadmeContentWrapper wrapper = ReadmeExtractor.getInstance().extract(REPO_FOLDER+"/README.md");

        Map<String, ReadmeContent> map = wrapper.topicContentMap();
        if(map == null){
            log.debug("Base readme file not found, or failed to extract from it");
            return null;
        }

        final ReadmeContent readmeContent = map.getOrDefault(INDEX_KEY.toLowerCase(Locale.US), null);

        if(readmeContent == null){
            log.debug("Failed to extract ReadmeContent from root readme");
            return null;
        }

        final String content = readmeContent.getContent().toString();

        if(content.isEmpty()) {
            log.debug("Failed to extract content from root readme");
            return null;
        }

        final String folderNames = content.substring( content.indexOf("```") + "```".length(), content.lastIndexOf("```") ).trim();

        final String[] folders = folderNames.replaceAll("[ ]","").split("[,]");

        log.info("All folders are: {}", Arrays.toString(folders));

        //final List<TopicWithSubTopic> indices = new ArrayList<>();
        final List<Topic> topics = new ArrayList<>();
        final List<SubTopic> subTopics = new ArrayList<>();
        final List<Blog> blogList = new ArrayList<>();

        int topicSerial = 0;
        for(String strFolder : folders){
            final File folder = new File(REPO_FOLDER+"/"+strFolder);

            final File rootReadme = new File(REPO_FOLDER+"/"+strFolder+"/README.md");

            final String topicName = getTopicNameFromFolder(strFolder);
            final Topic topic = new Topic( topicName, 0, strFolder, topicSerial ); // updated inside
            topicSerial++;
            topics.add(topic); // reference, no worry when adding

            if( rootReadme.exists() ){ // only one part available
                final String rawText = ReadmeExtractor.getInstance().extractRawText(rootReadme);
                if(rawText == null) {
                    log.debug("Failed to extract content from readme: {}",rootReadme.getName());
                    continue;
                }

                topic.setNoOfSubTopics(1);
                final SubTopic subTopic = new SubTopic(topicName, topicName, topic, 0);
                subTopics.add(subTopic);

                //final TopicWithSubTopic topicWithSubTopic = new TopicWithSubTopic(topic, List.of(subTopic));
                //indices.add(topicWithSubTopic);

                final Blog blog = new Blog(subTopic, rawText);
                blogList.add(blog);
            }
            else { // multiple part available

                final File[] folderParts = folder.listFiles();
                if(folderParts == null){
                    log.debug("Folder part is some how null at: {}",strFolder);
                    continue;
                }

                //final TopicWithSubTopic topicWithSubTopic = new TopicWithSubTopic(topic, new ArrayList<>());

                int serialOutside = folderParts.length + 1;
                for(File part : folderParts){
                    final String strPartName = part.getName();

                    if( !part.isDirectory() || "files".equalsIgnoreCase(strPartName) ) {
                        log.info("Ignoring in multiple sub topic as it is 'files' or directory");
                        continue;
                    }

                    int subTopicSerial = getSerialFromFolder(strPartName);
                    if(subTopicSerial == -1) {
                        serialOutside++;
                        subTopicSerial = serialOutside;
                    }

                    final File partReadme = new File( part,"/README.md" );

                    final String rawText = ReadmeExtractor.getInstance().extractRawText(partReadme);
                    if(rawText == null) {
                        log.debug("Failed to extract content from multiple part at readme: {}",partReadme.getName());
                        continue;
                    }

                    topic.setNoOfSubTopics( topic.getNoOfSubTopics()+1 );
                    final SubTopic subTopic = new SubTopic(topicName, strPartName, topic, subTopicSerial);
                    subTopics.add(subTopic);
                    //topicWithSubTopic.getSubTopicList().add(subTopic);

                    final Blog blog = new Blog(subTopic, rawText);
                    blogList.add(blog);
                }

                //indices.add(topicWithSubTopic);
            }
        }

        log.info("Data generation part completed with {} topics {} subtopics and {} blogs", topics.size(), subTopics.size(), blogList.size());

        return new Pair<>( new Pair<>(topics, subTopics) , blogList);
    }

    /**
     *
     * @param folder name of the folder of the format `part<number>`
     * @return part number if found else -1
     */
    private int getSerialFromFolder(String folder){
        final String reg = "part\\s*(\\d+)";

        final Matcher matcher = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(folder);

        if (matcher.find()) {
            return Integer.parseInt( matcher.group(1) );
        } else {
            return -1;
        }

    }

    /**
     *
     * @param folder name of the folder
     * @return the corresponding key in map or the folder name itself if key not found
     */
    public String getTopicNameFromFolder(String folder){
        if( folderValueMap.containsKey(folder) ) return folderValueMap.get(folder);

        return folder;
    }

}
