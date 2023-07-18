package com.team.team_project.service.unscribe;

import java.time.LocalDateTime;

public interface UnscribeService {
     // 바뀌면 true
    // 실패하면 false
    boolean userUnscribecomplete(String status, String nick);


    void userModDateUpdate(String nick);
}

