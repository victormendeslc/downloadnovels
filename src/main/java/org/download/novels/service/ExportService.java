package org.download.novels.service;

import jakarta.servlet.ServletOutputStream;

import java.io.IOException;

public interface ExportService {

    String export(String novelName);

    void export(String novelName, ServletOutputStream outputStream);

    void export(String novelName, ServletOutputStream outputStream, String html) throws IOException;
}
