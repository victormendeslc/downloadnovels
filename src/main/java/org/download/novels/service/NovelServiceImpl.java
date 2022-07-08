package org.download.novels.service;

import org.download.novels.enums.TypeSite;
import org.download.novels.repository.NovelRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NovelServiceImpl implements NovelService {

    @Autowired
    private NovelRepository repository;

    @Autowired
    private WriterFactory factory;

    @Override
    public void create(TypeSite type, String file, String page) {
        try {
            Novel novel = getNovel(type, file, page);
            if (Objects.nonNull(novel.getId()) && page.isEmpty()) {
                Chapter chapter = novel.lastChapter().orElseThrow();
                page = chapter.getNextChapter();
            }
            factory.executeByType(type).execute(novel, file, page);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Novel> getAllNovels() {
        return repository.findAll();
    }

    private Novel getNovel(TypeSite type, String file, String page) {
        return repository.findByNovelName(file).orElseGet(() -> {
            Novel entity = new Novel();
            entity.setNovelName(file);
            entity.setPage(page);
            entity.setType(type);
            repository.save(entity);
            repository.flush();
            return entity;
        });
    }
}
