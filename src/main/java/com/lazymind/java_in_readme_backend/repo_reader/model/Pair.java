package com.lazymind.java_in_readme_backend.repo_reader.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Pair<FIRST, SECOND> {
    private final FIRST first;
    private final SECOND second;
}
