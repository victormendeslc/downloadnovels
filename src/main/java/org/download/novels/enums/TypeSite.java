package org.download.novels.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeSite {
    LIGHTNOVEL("LightNovel- FAIL"),
    NOVELPUB("Novelpub - FAIL"),
    WUXIAWORLDSITE("Wuxiaworldsite - DRIVER"),
    WUXIAWORLD("Wuxiaworld - DRIVER"),
    NOVELHALL("NovelHall"),
    REAPERSCANS("Reaperscans"),
    WOOPREAD("Woopread"),
    NEOXSCANS("NeoxScans"),
    KOREANMTL("koreanmtl"),
    CENTRALNOVEL("CentralNovel");

    private final String title;

}
