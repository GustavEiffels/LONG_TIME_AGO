package com.team.team_project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendDTO {
    private Long fno;
    private String status;
    private Long request;
    private Long response;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
