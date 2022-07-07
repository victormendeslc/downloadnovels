package org.download.novels.repository;

import org.download.novels.repository.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
