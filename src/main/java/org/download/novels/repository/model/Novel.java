package org.download.novels.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.download.novels.enums.TypeSite;

import java.util.*;

@Entity
@Table
@Getter
@Setter
public class Novel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false, length = 16)
    private UUID id;

    @Column
    private String page;

    @Column
    private String novelName;

    @Column
    private TypeSite type;

    @OneToMany
    @JoinColumn(name = "novel_id")
    private Set<Chapter> chapters = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Novel novel = (Novel) o;
        return Objects.equals(id, novel.id) && Objects.equals(page, novel.page) && Objects.equals(novelName, novel.novelName) && type == novel.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, page, novelName, type);
    }

    public Optional<Chapter> lastChapter() {
        return this.getChapters().stream()
                .sorted(Comparator.comparingInt(Chapter::getPage))
                .reduce((first, second) -> second);
    }
}
