package org.download.novels.extractor;

import org.download.novels.repository.model.Novel;

public interface IExtractor {

    void execute(Novel novel, String file, String url);

}
