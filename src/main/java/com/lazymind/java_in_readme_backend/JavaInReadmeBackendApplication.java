package com.lazymind.java_in_readme_backend;

import com.lazymind.java_in_readme_backend.model.ReadmeContentWrapper;
import com.lazymind.java_in_readme_backend.repo_reader.DataGenerator;
import com.lazymind.java_in_readme_backend.repo_reader.ReadmeExtractor;
import com.lazymind.java_in_readme_backend.repo_reader.RepoReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JavaInReadmeBackendApplication {

	private static final Logger log = LoggerFactory.getLogger(JavaInReadmeBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JavaInReadmeBackendApplication.class, args);

		new Thread(new Runnable() {
			@Override
			public void run() {
//				RepoReader repoReader = new RepoReader();
//				boolean isUpdated = repoReader.updateCodebase();
//				log.info("repo updated: {}",isUpdated);


//				ReadmeContentWrapper content = ReadmeExtractor.getInstance().extract("source/README.md");
//				log.info("Total content found: {}",content.readmeContent());

				DataGenerator.getInstance().generateAndSaveData();
			}
		}).start();
	}

}
