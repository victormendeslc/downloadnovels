package org.download.novels.service.driver.neoxscans;

import org.download.novels.extractor.Writer;
import org.download.novels.repository.ChapterRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

@Service
public class NeoxScans extends Writer {

    protected NeoxScans(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String extractTitle(WebDriver driver) {
        return driver.findElement(By.id("chapter-heading")).getAttribute("innerHTML");
    }

    @Override
    protected String extractContent(WebDriver driver) {
        return driver.findElement(By.cssSelector("div.reading-content")).getAttribute("innerHTML");
    }

    @Override
    protected String nextPage(WebDriver driver) {
        return driver.findElement(By.cssSelector("div.nav-next > a")).getAttribute("href");
    }


}
