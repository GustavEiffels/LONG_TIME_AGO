package com.team.team_project.service.pwCheck_And_DuplicateCheck;


public interface PwAndDupCheck {

    /** pw 와 pwCheck 가 일치하는지 판단하는 method
     */
    boolean isPwEqual(String pw, String pwCheck);

    boolean nickDuplicateCheck(String nick, String currentNick);


}
