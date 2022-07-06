package org.download.novels.service.novehall;


import org.download.novels.leitor.Writer;
import org.download.novels.repository.ChapterRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

@Service
public class NovelHall extends Writer {

    public NovelHall(ChapterRepository chapterRepository) {
        super(chapterRepository);
    }

    @Override
    protected String extractTitle(ChromeDriver driver) {
        return driver.findElement(By.cssSelector("div.single-header")).findElement(By.tagName("h1")).getText();
    }

    @Override
    protected String extractContent(ChromeDriver driver) {
        WebElement content = driver.findElement(By.cssSelector("div.entry-content"));
        return content.getAttribute("innerHTML");
    }

    @Override
    protected String nextPage(ChromeDriver driver) {
        WebElement navigatorElement = driver.findElement(By.cssSelector("nav.nav-single"));
        boolean naoExiste = navigatorElement.findElements(By.linkText("Next")).isEmpty();
        if (!naoExiste && !navigatorElement.findElement(By.linkText("Next")).getAttribute("href").isEmpty()) {
            return navigatorElement.findElement(By.linkText("Next")).getAttribute("href");
        }
        return null;
    }
}
