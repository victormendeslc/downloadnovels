package org.download.novels.service;

import jakarta.servlet.ServletOutputStream;

public interface ExportService {

    String export(String novelName);

    void export(String novelName, ServletOutputStream outputStream);
}
