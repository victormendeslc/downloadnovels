package org.download.novels.service.reaperscans;

import org.download.novels.leitor.Writer;
import org.download.novels.repository.ChapterRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class Reaperscans extends Writer {

    public Reaperscans(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String extractTitle(ChromeDriver driver) {
        return driver.findElement(By.cssSelector("li.active")).getText();
    }

    @Override
    protected String extractContent(ChromeDriver driver) {
        WebElement element = driver.findElement(By.cssSelector("div.reading-content"));
        return element.findElement(By.cssSelector("div.text-left")).getAttribute("innerHTML");
    }

    @Override
    protected String nextPage(ChromeDriver driver) {
        WebElement chapterPage = driver.findElement(By.cssSelector("div.nav-next"));
        boolean exists = !chapterPage.findElements(By.tagName("a")).isEmpty();
        if (exists) {
            return chapterPage.findElement(By.tagName("a")).getAttribute("href");
        }
        return null;
    }
}
