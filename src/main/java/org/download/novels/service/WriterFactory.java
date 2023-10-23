package org.download.novels.service;

import lombok.RequiredArgsConstructor;
import org.download.novels.enums.TypeSite;
import org.download.novels.extractor.IExtractor;
import org.download.novels.service.driver.lightnovel.LightNovel;
import org.download.novels.service.driver.neoxscans.NeoxScans;
import org.download.novels.service.driver.wuxiaworld.Wuxiaworld;
import org.download.novels.service.http.centralnovel.CentralNovel;
import org.download.novels.service.http.lightnovelbrasil.LightNovelBrasil;
import org.download.novels.service.http.novehall.NovelHallHttp;
import org.download.novels.service.http.novelpub.NovelPubHttp;
import org.download.novels.service.http.reaperscans.ReaperscansHttp;
import org.download.novels.service.http.skydemonorder.Koreanmtl;
import org.download.novels.service.http.vulcan.Vulcan;
import org.download.novels.service.http.woopread.Woopread;
import org.download.novels.service.http.wuxiacity.WuxiaCity;
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
    private final LightNovelBrasil lightNovelBrasil;
    private final WuxiaCity wuxiacity;
    private final Vulcan vulcan;


    public IExtractor executeByType(TypeSite type) {
        return switch (type) {
            case WUXIAWORLD -> wuxiaworld;
            case NOVELHALL -> novelHall;
            case REAPERSCANS -> reaperscans;
            case WOOPREAD, WUXIAWORLDSITE -> woopread;
            case KOREANMTL -> koreanmtl;
            case NEOXSCANS -> neoxScans;
            case CENTRALNOVEL -> centralNovel;
            case LIGHTNOVELBRASIL -> lightNovelBrasil;
            case WUXIACITY -> wuxiacity;
            case VULCAN -> vulcan;
        };
    }
}
