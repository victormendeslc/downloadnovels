package org.download.novels.service;

import j2html.tags.ContainerTag;
import org.download.novels.enums.TypeSite;
import org.download.novels.extractor.IExtractor;
import org.download.novels.repository.NovelRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;
import org.download.novels.service.driver.lightnovel.LightNovel;
import org.download.novels.service.http.novehall.NovelHallHttp;
import org.download.novels.service.http.reaperscans.ReaperscansHttp;
import org.download.novels.service.driver.wuxiaworld.Wuxiaworld;
import org.springframework.stereotype.Service;

import java.util.List;

import static j2html.TagCreator.*;

@Service
public record NovelServiceImpl(NovelRepository repository,
                               Wuxiaworld wuxiaworld,
                               LightNovel lightNovel,
                               NovelHallHttp novelHall,
                               ReaperscansHttp reaperscans) implements NovelService {

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

    private IExtractor executeByType(TypeSite type) {
        return switch (type) {
            case WUXIAWORLD -> wuxiaworld;
            case LIGHTNOVEL -> lightNovel;
            case NOVELHALL -> novelHall;
            case REAPERSCANS -> reaperscans;
        };
    }

    @Override
    public List<Novel> getAllNovels() {
        return repository.findAll();
    }


    public String export(String novelName) {
        Novel novel = repository.findByNovelName(novelName).orElseThrow();
        List<Chapter> chapters = novel.getChapters().stream().sorted().toList();
        return html(
                head(title(novel.getNovelName())),
                body(
                        h1(novelName),
                        label("Index"),
                        ol(createIndex(chapters)),
                        div(attrs("#container"), createContent(chapters))
                )
        ).render();
    }

    private ContainerTag[] createContent(List<Chapter> chapters) {
        return chapters.stream()
                .map(chapter ->
                        div(
                                rawHtml(chapter.getTitle()),
                                rawHtml(chapter.getContent())
                        )
                )
                .toArray(ContainerTag[]::new);
    }

    private ContainerTag[] createIndex(List<Chapter> chapters) {
        return chapters.stream()
                .map(chapter ->
                        li(
                                a("Chapter " + chapter.getChapter())
                                        .withHref("chapter" + chapter.getChapter())
                        )

                )
                .toArray(ContainerTag[]::new);
    }
}
