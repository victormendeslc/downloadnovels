package org.download.novels.service;

import org.download.novels.enums.TypeSite;
import org.download.novels.pojo.Website;
import org.download.novels.repository.NovelRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }

    @Override
    public void create(Novel entity) {
        try {
            Novel novel = repository.findByNovelName(entity.getNovelName()).orElseGet(() -> {
                repository.save(entity);
                repository.flush();
                return entity;
            });
            String page = Optional.of(entity.getProloguePage()).orElse(novel.getPage());
            factory.executeByType(novel.getType()).execute(novel, page, Objects.nonNull(entity.getProloguePage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
