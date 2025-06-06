package com.lazymind.java_in_readme_backend.db.repo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "repo_table")
public class MyRepo {

    @Id
    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "url")
    @JsonProperty("url")
    private String url;

    @Column(name = "serial")
    @JsonProperty("serial")
    private Integer serial;

}
