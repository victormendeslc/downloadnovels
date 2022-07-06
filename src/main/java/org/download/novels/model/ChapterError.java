package org.download.novels.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class ChapterError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer chapter;

    @Column(length = 1000)
    private String url;

    @ManyToOne
    @JoinColumn(name = "novel_id")
    private Novel novel;
}
