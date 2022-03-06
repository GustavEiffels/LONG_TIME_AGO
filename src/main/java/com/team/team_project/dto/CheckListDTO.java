package com.team.team_project.dto;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CheckListDTO {
    private String todo;
    private String done;
    private Long pno;
    private Long code;
}

