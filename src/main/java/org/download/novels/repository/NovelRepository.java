package org.download.novels.repository;

import org.download.novels.repository.model.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NovelRepository extends JpaRepository<Novel, Long> {

    Optional<Novel> findByNovelName(String novelName);
}
