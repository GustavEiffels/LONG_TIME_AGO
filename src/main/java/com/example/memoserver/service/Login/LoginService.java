package com.example.memoserver.service.Login;

import org.json.JSONObject;

import java.util.Map;

public interface LoginService
{

  /** Login 을 위한 method  */
  /** LoginFragment ----> login */
  Map<String, Object> login(String userId, String userPw);

  int autoUpdate(int userAuto, String userId);

  String getUserAutoInfo(Long userIdx);

  String getUserNick(Long userIdx);

  // LogOut
  void logout(Long user_idx, int user_auto_login);





  // Google Account check
  String googleCheck(String email);


  // Get Google Account
  JSONObject googleAccount(String email);

  /** user id 로 user email 을 가져오는 Method */
  /** login 했을 때, 계정이 탈퇴 계정일 경우 id 를 사용해서 user Email 을 가져옴 */
  /** LoginFragment ------> notAvailable*/
  String notAvailable(String id);


}
