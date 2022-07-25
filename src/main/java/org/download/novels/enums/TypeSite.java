package org.download.novels.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeSite {
    LIGHTNOVEL("LightNovel- FAIL"),
    WUXIAWORLD("Wuxiaworld - DRIVER"),
    NOVELHALL("NovelHall"),
    REAPERSCANS("Reaperscans"),
    NOVELPUB("Novelpub - FAIL"),
    WOOPREAD("Woopread"),
    WUXIAWORLDSITE("Wuxiaworldsite - DRIVER"),

    NEOXSCANS("NeoxScans"),
    KOREANMTL("koreanmtl");

    private final String title;

}
