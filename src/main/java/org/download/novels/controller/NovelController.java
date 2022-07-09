package org.download.novels.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.download.novels.enums.TypeSite;
import org.download.novels.repository.model.Novel;
import org.download.novels.service.ExportService;
import org.download.novels.service.NovelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public record NovelController(ExportService exportService, NovelService service) {

    @GetMapping("/exportHtml")
    public String download(Model model) {
        model.addAttribute("novels", service.getAllNovels());
        return "exportHtml";
    }

    @PostMapping("/exportPdf")
    @ResponseBody
    public void downloadFile(@ModelAttribute(name = "novelForm") Novel novel, Model model, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + novel.getNovelName().concat(".pdf");
        response.setHeader(headerKey, headerValue);

        exportService.export(novel.getNovelName(), response.getOutputStream());
    }

    @PostMapping("/exportHtml")
    @ResponseBody
    public String exportHtml(@ModelAttribute(name = "novelForm") Novel novel, Model m, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + novel.getNovelName().concat(".html");
        response.setHeader(headerKey, headerValue);

        exportService.export(novel.getNovelName(), response.getOutputStream(), "html");
        return "exportHtml";
    }


    @PostMapping("/download")
    public String downloadPage(@ModelAttribute(name = "novelForm") Novel novel, Model m) {
        String novelName = novel.getNovelName();
        String page = novel.getPage();
        TypeSite type = novel.getType();

        service.create(type, novelName, page);

        return "index";

    }
}


