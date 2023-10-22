package org.download.novels.service;

import lombok.RequiredArgsConstructor;
import org.download.novels.enums.TypeSite;
import org.download.novels.extractor.IExtractor;
import org.download.novels.service.driver.lightnovel.LightNovel;
import org.download.novels.service.driver.neoxscans.NeoxScans;
import org.download.novels.service.driver.wuxiaworld.Wuxiaworld;
import org.download.novels.service.http.centralnovel.CentralNovel;
import org.download.novels.service.http.novehall.NovelHallHttp;
import org.download.novels.service.http.novelpub.NovelPubHttp;
import org.download.novels.service.http.reaperscans.ReaperscansHttp;
import org.download.novels.service.http.skydemonorder.Koreanmtl;
import org.download.novels.service.http.woopread.Woopread;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriterFactory {

    private final Wuxiaworld wuxiaworld;
    private final LightNovel lightNovel;
    private final NovelHallHttp novelHall;
    private final ReaperscansHttp reaperscans;
    private final NovelPubHttp novelPubHttp;
    private final Woopread woopread;
    private final NeoxScans neoxScans;
    private final Koreanmtl koreanmtl;
    private final CentralNovel centralNovel;

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
            case CENTRALNOVEL -> centralNovel;
        };
    }
}
