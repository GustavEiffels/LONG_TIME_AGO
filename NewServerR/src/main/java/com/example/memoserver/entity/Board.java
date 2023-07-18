package com.example.memoserver.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@DynamicInsert
@Table(name="Board",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"board_name"})
        }
        )
public class Board
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_idx;

    @Column(name="board_name",length =100)
    private String board_name;
}
