package com.lazymind.java_in_readme_backend.utility;

import com.lazymind.java_in_readme_backend.repo_reader.model.ReadmeContent;
import com.lazymind.java_in_readme_backend.repo_reader.model.ReadmeContentWrapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

@Slf4j
public final class ReadmeExtractor {

    private static final class InstanceHolder {
        private static final ReadmeExtractor instance = new ReadmeExtractor();
    }
    public static ReadmeExtractor getInstance(){
        return InstanceHolder.instance;
    }
    private ReadmeExtractor(){}

    public String extractRawText(File file){
        if(!file.exists()) {
            log.debug("File not found in extractRawText");
            return null;
        }

        final String extension = getExtension(file);
        if( !"md".equalsIgnoreCase(extension) ) {
            log.debug("Wrong file provided in extractRawText: {}",extension);
            return null;
        }

        final StringBuilder builder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){

            String line;
            while ( (line = reader.readLine()) != null ){
                builder.append(line);
            }

            return builder.toString();
        }catch (IOException e){
            log.error("Some error occurred at extractRawText: {}",e.getMessage());
            return null;
        }

    }
    /**
     *
     * @param filePath path of the readme file
     * @return list of readme objects, or empty list. It will never return null
     */
    @NonNull
    public ReadmeContentWrapper extract(String filePath){
        final File file = new File(filePath);
        if(!file.exists()){
            log.debug("File not found: {}",filePath);
            return new ReadmeContentWrapper(null, null);
        }

        final String extension = getExtension(file);
        if( !"md".equalsIgnoreCase(extension) ) {
            log.debug("Wrong file type provided: {}",filePath);
            return new ReadmeContentWrapper(null, null);
        }

        try( BufferedReader reader = new BufferedReader(new FileReader(file)) ){

            final List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if(!line.isEmpty()){
                    lines.add(line);
                }
            }

            final ReadmeContent parentContent = new ReadmeContent(0,"Root");
            final Map<String, ReadmeContent> titleContentMap = new TreeMap<>();
            log.info("Calling recursive read");

            readRecursive(lines, titleContentMap,0, parentContent);

            log.info("Returning from recursive read");
            return new ReadmeContentWrapper(parentContent, titleContentMap);
        }catch (IOException e){
            log.debug("Unable to read file: {}",e.getMessage());
            return new ReadmeContentWrapper(null, null);
        }
    }

    /**
     *
     * @param currentContent to add content
     * @throws IOException exception to throw
     * @return the index up-to processed so far
     */
    private int readRecursive(List<String> lines, Map<String, ReadmeContent> titleContentMap, int index, ReadmeContent currentContent) throws IOException {
        while (index < lines.size()){
            final String line = lines.get(index);

            final int curLevel = countHashAtStart(line);
            if(curLevel == 0){ // not any heading
                currentContent.getContent().append(line);
                index++;
            }
            else {
                final String title = line.substring( line.indexOf( "#".repeat(curLevel)) + curLevel ).trim();
                final ReadmeContent content = new ReadmeContent(curLevel, title);
                titleContentMap.put(title.toLowerCase(Locale.US), content);

                if( curLevel > currentContent.getLevel()){ // at nested level
                    index = readRecursive(lines, titleContentMap,index+1, content);
                }
                else{
                    // revert the last readLine()
                    break;
                }

                currentContent.addContent( content );
            }
        }
//        if( !builder.isEmpty() ) {
//            final ReadmeContent content = new ReadmeContent(parentLevel, parentTitle, builder.toString());
//            currentContent.getNestedContents().add(content);
//        }
        return index;
    }

    private int countHashAtStart(String line){
        int index = 0;
        while (index < line.length() && line.charAt(index) == '#') index++;

        return index;
    }

    private String getExtension(File file){
        final String fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i+1);
        }
        return null;
    }

}
