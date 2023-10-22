package org.download.novels.service;

import org.download.novels.enums.TypeSite;
import org.download.novels.pojo.Website;
import org.download.novels.repository.NovelRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelRepository repository;

    @Autowired
    private WriterFactory factory;


    @Override
    public List<Novel> getAllNovels() {
        return repository.findAll();
    }

    @Override
    public List<Website> getAllWebSites() {
        return Arrays.stream(TypeSite.values())
                .map(Website::new)
                .sorted()
                .toList();
    }

    @Override
    public void create(Novel entity) {
        try {
            Novel novel = repository.findByNovelName(entity.getNovelName()).orElseGet(() -> {
                repository.save(entity);
                repository.flush();
                return entity;
            });

            var page = getStartPage(entity, novel);
            var extrator = factory.executeByType(novel.getType());
            if (extrator != null) {
                extrator.execute(novel, page, StringUtils.hasText(entity.getProloguePage()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getStartPage(Novel entity, Novel novel) {
        if (CollectionUtils.isEmpty(novel.getChapters())) {
            return entity.getProloguePage() != null && !entity.getProloguePage().isEmpty() ? entity.getProloguePage() : novel.getPage();
        }

        Chapter chapter = novel.getChapters().stream().max(Comparator.naturalOrder()).orElseThrow();
        return chapter.getNextChapter();
    }

}
