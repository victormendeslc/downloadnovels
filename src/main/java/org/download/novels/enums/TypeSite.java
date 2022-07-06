package org.download.novels.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeSite {
    LIGHTNOVEL("LightNovel"),
    WUXIAWORLD("Wuxiaworld"),
    NOVELHALL("NovelHall");

    private final String title;

}
