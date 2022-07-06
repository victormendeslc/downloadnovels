package org.download.novels.leitor;

import org.download.novels.model.Novel;

import java.io.IOException;

public interface Extractor {

    void execute(Novel novel, String file, String url);

}
