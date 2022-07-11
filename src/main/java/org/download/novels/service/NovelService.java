package org.download.novels.service;

import org.download.novels.pojo.Website;
import org.download.novels.repository.model.Novel;

import java.util.List;

public interface NovelService {

    List<Novel> getAllNovels();

    List<Website> getAllWebSites();

    void create(Novel novel);
}
