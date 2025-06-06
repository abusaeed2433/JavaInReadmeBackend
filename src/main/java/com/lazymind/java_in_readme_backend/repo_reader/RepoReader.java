package com.lazymind.java_in_readme_backend.repo_reader;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class RepoReader {

    private static final String REPO_OWNER = "abusaeed2433";
    private static final String REPO_NAME = "JavaInREADME";
    public static final String REPO_FOLDER = "source";

    private static final class InstanceHolder {
        private static final RepoReader instance = new RepoReader();
    }

    public static RepoReader getInstance(){
        return InstanceHolder.instance;
    }

    public boolean updateCodebase(String accessToken){

        final File file = new File(REPO_FOLDER);

        if( file.exists() ){
            return pullRepo(file);
        }

        return cloneRepo(accessToken);
    }

    private boolean pullRepo(File destination){
        ProcessBuilder builder = new ProcessBuilder("git", "pull");
        builder.directory(destination);
        return runProcess(builder);
    }

    private boolean cloneRepo(String accessToken){
        final String repoUrl = "https://" + accessToken + "@github.com/" + REPO_OWNER + "/" + REPO_NAME;
        ProcessBuilder builder = new ProcessBuilder("git", "clone", repoUrl, REPO_FOLDER);
        return runProcess(builder);
    }

    private boolean runProcess(ProcessBuilder process) {
        try {
            final int exitCode = process.start().waitFor();
            return ( exitCode == 0);
        }catch (IOException | InterruptedException e){
            log.error("Error at: {}",e.getMessage());
            return false;
        }
    }

}
