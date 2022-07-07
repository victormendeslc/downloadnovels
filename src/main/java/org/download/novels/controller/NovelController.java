package org.download.novels.controller;

import lombok.AllArgsConstructor;
import org.download.novels.enums.TypeSite;
import org.download.novels.repository.model.Novel;
import org.download.novels.service.NovelServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor
@Controller
public class NovelController {

    private final NovelServiceImpl service;

    @GetMapping("/exportHtml")
    public String download(Model model) {
        model.addAttribute("novels", service.getAllNovels());
        return "exportHtml";
    }

    @PostMapping("/exportHtml")
    @ResponseBody
    public String exportHtml(@ModelAttribute(name = "novelForm") Novel novel, Model m) {
        return service.export(novel.getNovelName());
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


