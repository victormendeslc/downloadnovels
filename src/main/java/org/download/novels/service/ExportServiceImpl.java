package org.download.novels.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import j2html.tags.ContainerTag;
import jakarta.servlet.ServletOutputStream;
import lombok.AllArgsConstructor;
import org.download.novels.repository.NovelRepository;
import org.download.novels.repository.model.Chapter;
import org.download.novels.repository.model.Novel;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static j2html.TagCreator.*;


@AllArgsConstructor
@Service
public class ExportServiceImpl implements ExportService {

    private final NovelRepository repository;


    public String export(String novelName) {
        Novel novel = repository.findByNovelName(novelName).orElseThrow();
        List<Chapter> chapters = novel.getChapters().stream().sorted().toList();

        return html(
                head(
                        title(novel.getNovelName()),
                        meta().attr("charset", "UTF-8")),
                body(
                        h1(novelName),
                        label("Index"),
                        ul(createIndex(chapters)),
                        div(attrs("#container"), createContent(chapters))
                )
        ).attr("lan","pt-BR").render();
    }

    @Override
    public void export(String novelName, ServletOutputStream outputStream) {
        org.jsoup.nodes.Document doc = Jsoup.parse(export(novelName));
        doc.select("img").remove();
        doc.select("script").remove();
        var novoHtml = doc.html();
        toPdf(outputStream, novoHtml, novelName);
    }

    @Override
    public void export(String novelName, ServletOutputStream outputStream, String exportHtml) throws IOException {
        String html = export(novelName);
        outputStream.write(html.getBytes(StandardCharsets.UTF_8));
    }

    private void toPdf(ServletOutputStream os, String html, String novelName) {
        String filename = novelName.concat(".pdf");
        try {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            Document document = new W3CDom().fromJsoup(Jsoup.parse(html));
            builder.withW3cDocument(document, filename);
            builder.toStream(os);
            builder.run();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ContainerTag[] createContent(List<Chapter> chapters) {
        return chapters.stream()
                .map(chapter ->
                        div(
                                rawHtml(chapter.getTitle()),
                                rawHtml(chapter.getContent())
                        )
                )
                .toArray(ContainerTag[]::new);
    }

    private ContainerTag[] createIndex(List<Chapter> chapters) {
        return chapters.stream()
                .map(chapter ->
                        li(
                                a("Chapter " + chapter.getNovelIndex())
                                        .withHref("#chapter" + chapter.getPage())
                        )

                )
                .toArray(ContainerTag[]::new);
    }
}
