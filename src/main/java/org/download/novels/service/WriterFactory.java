package org.download.novels.service;

import org.download.novels.enums.TypeSite;
import org.download.novels.extractor.IExtractor;
import org.download.novels.repository.NovelRepository;
import org.download.novels.service.driver.lightnovel.LightNovel;
import org.download.novels.service.driver.wuxiaworld.Wuxiaworld;
import org.download.novels.service.driver.neoxscans.NeoxScans;
import org.download.novels.service.http.novehall.NovelHallHttp;
import org.download.novels.service.http.novelpub.NovelPubHttp;
import org.download.novels.service.http.reaperscans.ReaperscansHttp;
import org.download.novels.service.http.skydemonorder.Koreanmtl;
import org.download.novels.service.http.woopread.Woopread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterFactory {

    @Autowired
    private NovelRepository repository;
    @Autowired
    private Wuxiaworld wuxiaworld;
    @Autowired
    private LightNovel lightNovel;
    @Autowired
    private NovelHallHttp novelHall;
    @Autowired
    private ReaperscansHttp reaperscans;
    @Autowired
    private NovelPubHttp novelPubHttp;
    @Autowired
    private Woopread woopread;
    @Autowired
    private NeoxScans neoxScans;

    @Autowired
    private Koreanmtl koreanmtl;

    public IExtractor executeByType(TypeSite type) {
        return switch (type) {
            case WUXIAWORLD -> wuxiaworld;
            case LIGHTNOVEL -> lightNovel;
            case NOVELHALL -> novelHall;
            case REAPERSCANS -> reaperscans;
            case NOVELPUB -> novelPubHttp;
            case WOOPREAD, WUXIAWORLDSITE -> woopread;
            case KOREANMTL -> koreanmtl;
            case NEOXSCANS -> neoxScans;
        };
    }
}
