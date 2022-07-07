package org.download.novels.service;

import org.download.novels.enums.TypeSite;
import org.download.novels.repository.model.Novel;

import java.util.List;

public interface NovelService {
    void create(TypeSite type, String file, String page);

    String export(String novelName);

    List<Novel> getAllNovels();
}
