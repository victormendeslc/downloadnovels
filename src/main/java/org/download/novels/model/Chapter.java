package org.download.novels.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false , length = 16)
    private UUID id;

    @Column
    private Integer chapter;

    @Column
    private String title;

    @Column(length = 100000)
    private String content;

    @Column(length = 1000)
    private String nextChapter;

    @ManyToOne
    @JoinColumn(name = "novel_id")
    private Novel novel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter1 = (Chapter) o;
        return Objects.equals(id, chapter1.id) && Objects.equals(chapter, chapter1.chapter) && Objects.equals(title, chapter1.title) && Objects.equals(content, chapter1.content) && Objects.equals(nextChapter, chapter1.nextChapter) && Objects.equals(novel, chapter1.novel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chapter, title, content, nextChapter, novel);
    }
}
