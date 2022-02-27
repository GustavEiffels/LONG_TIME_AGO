package com.team.team_project.dto.editInfoDTO;

import lombok.*;

import javax.annotation.Nullable;
import javax.validation.constraints.Pattern;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditInfoDTO {

    /*** \
     * not null 을 제거
     * 유효성이 필요한 password , nick name , answer
     */
    @Nullable
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "Password must be 8 to 16 characters in uppercase and lowercase letters, numbers, and special characters.")
    private String pw;


    @Nullable
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "not type of nick , please insert nickname type")
    private String nick;

    @Nullable
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "answer type is Korean, numeric, English 2~20 characters")
    private String answer;
}
