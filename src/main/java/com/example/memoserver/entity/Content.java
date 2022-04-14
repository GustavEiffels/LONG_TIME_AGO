package com.example.memoserver.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name="Content")
public class Content
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long content_idx;


    @Column(length = 50)
    private String content_subject;

    private LocalDateTime content_write_date;

    @Column(columnDefinition = "LONGTEXT")
    private String content_text;

    @Column(length = 50)
    private String content_image;


    private String content_image_url;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "content_writer_idx")
    private User content_writer_idx;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "content_board_idx")
    private Board content_board_idx;



}
