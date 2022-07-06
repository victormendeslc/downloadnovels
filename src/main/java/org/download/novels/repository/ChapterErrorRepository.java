package org.download.novels.repository;

import org.download.novels.model.ChapterError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterErrorRepository extends JpaRepository<ChapterError, Long> {
}
