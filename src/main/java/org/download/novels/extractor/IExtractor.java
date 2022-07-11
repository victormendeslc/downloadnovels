package org.download.novels.extractor;

import org.download.novels.repository.model.Novel;

public interface IExtractor {

    void execute(Novel novel, String url);

    void execute(Novel novel, String page, boolean prologue);
}
