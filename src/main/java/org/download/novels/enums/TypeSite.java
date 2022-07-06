package org.download.novels.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeSite {
    LIGHTNOVEL("LightNovel"),
    WUXIAWORLD("Wuxiaworld"),
    NOVELHALL("NovelHall"),
    REAPERSCANS("Reaperscans");

    private final String title;

}
