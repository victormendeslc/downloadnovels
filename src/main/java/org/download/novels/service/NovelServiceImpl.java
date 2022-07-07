package org.download.novels.service;

import org.download.novels.enums.TypeSite;
import org.download.novels.extractor.IExtractor;
import org.download.novels.repository.NovelRepository;
import org.download.novels.repository.model.Novel;
import org.download.novels.service.driver.lightnovel.LightNovel;
import org.download.novels.service.driver.wuxiaworld.Wuxiaworld;
import org.download.novels.service.http.novehall.NovelHallHttp;
import org.download.novels.service.http.novelpub.NovelPubHttp;
import org.download.novels.service.http.reaperscans.ReaperscansHttp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record NovelServiceImpl(NovelRepository repository,
                               Wuxiaworld wuxiaworld,
                               LightNovel lightNovel,
                               NovelHallHttp novelHall,
                               ReaperscansHttp reaperscans,
                               NovelPubHttp novelPubHttp) implements NovelService {

    @Override
    public void create(TypeSite type, String file, String page) {
        try {
            Novel novel = repository.findByNovelName(file).orElseGet(() -> {
                Novel entity = new Novel();
                entity.setNovelName(file);
                entity.setPage(page);
                entity.setType(type);
                repository.save(entity);
                repository.flush();
                return entity;
            });
            executeByType(type).execute(novel, file, page);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Novel> getAllNovels() {
        return repository.findAll();
    }

    private IExtractor executeByType(TypeSite type) {
        return switch (type) {
            case WUXIAWORLD -> wuxiaworld;
            case LIGHTNOVEL -> lightNovel;
            case NOVELHALL -> novelHall;
            case REAPERSCANS -> reaperscans;
            case NOVELPUB -> novelPubHttp;
        };
    }


}
