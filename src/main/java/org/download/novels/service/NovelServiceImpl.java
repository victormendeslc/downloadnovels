package org.download.novels.service;

import j2html.tags.ContainerTag;
import org.download.novels.enums.TypeSite;
import org.download.novels.model.Novel;
import org.download.novels.repository.NovelRepository;
import org.download.novels.service.lightnovel.LightNovel;
import org.download.novels.service.novehall.NovelHall;
import org.download.novels.service.reaperscans.Reaperscans;
import org.download.novels.service.wuxiaworld.Wuxiaworld;
import org.springframework.stereotype.Service;

import java.util.List;

import static j2html.TagCreator.*;

@Service
public record NovelServiceImpl(NovelRepository repository,
                               Wuxiaworld wuxiaworld,
                               LightNovel lightNovel,
                               NovelHall novelHall,
                               Reaperscans reaperscans) implements NovelService {

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

            switch (type) {
                case WUXIAWORLD -> wuxiaworld.execute(novel, file, page);
                case LIGHTNOVEL -> lightNovel.execute(novel, file, page);
                case NOVELHALL -> novelHall.execute(novel, file, page);
                case REAPERSCANS -> reaperscans.execute(novel, file, page,true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Novel> getAllNovels() {
        return repository.findAll();
    }


    public String export(String novelName) {
        Novel novel = repository.findByNovelName(novelName).orElseThrow();

        String render = html(
                head(title(novel.getNovelName())),
                body(
                        h1(novelName),
                        label("Index"),
                        ol(createIndex(novel)),
                        div(attrs("#container"), createContent(novel))
                )
        ).render();
        return render;
    }

    private ContainerTag[] createContent(Novel novel) {
        return novel.getChapters().stream()
                .map(chapter ->
                        div(
                                rawHtml(chapter.getTitle()),
                                rawHtml(chapter.getContent())
                        )
                )
                .toArray(ContainerTag[]::new);
    }

    private ContainerTag[] createIndex(Novel novel) {
        return novel.getChapters().stream()
                .map(chapter ->
                        li(
                                a("Chapter " + chapter.getChapter())
                                        .withHref("chapter" + chapter.getChapter())
                        )

                )
                .toArray(ContainerTag[]::new);
    }
}
