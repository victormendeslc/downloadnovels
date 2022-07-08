package org.download.novels.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeSite {
    LIGHTNOVEL("LightNovel"),
    WUXIAWORLD("Wuxiaworld"),
    NOVELHALL("NovelHall"),
    REAPERSCANS("Reaperscans"),
    NOVELPUB("Novelpub"),
    WOOPREAD("Woopread");

    private final String title;

}
