package org.download.novels.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table
public class Chapter implements Comparable<Chapter> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "char" ,name = "id", updatable = false, unique = true, nullable = false , length = 36)
    private UUID id;

    @Column
    private Integer page;

    @Column
    private String novelIndex;

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
        Chapter chapter = (Chapter) o;
        return Objects.equals(id, chapter.id) && Objects.equals(page, chapter.page) && Objects.equals(title, chapter.title) && Objects.equals(content, chapter.content) && Objects.equals(nextChapter, chapter.nextChapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, page, title, content, nextChapter);
    }

    @Override
    public int compareTo(Chapter o) {
        return this.page - o.getPage();
    }

}
