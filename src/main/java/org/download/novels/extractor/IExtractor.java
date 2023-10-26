package org.download.novels.extractor;

import org.download.novels.repository.model.Novel;

public interface IExtractor {
    void executeFirst(Novel novel, String page, boolean prologue);
    void execute(Novel novel, String url);


}
