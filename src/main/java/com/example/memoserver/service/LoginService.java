package com.example.memoserver.service;

import java.util.Map;

public interface LoginService
{
  Map<String, Object> login(String userId, String userPw);

  int autoUpdate(int userAuto, String userId);

  String getUserAutoInfo(Long userIdx);

  String getUserNick(Long userIdx);

  // LogOut
  void logout(Long user_idx, int user_auto_login);

  // get User Pw
  String getUserPassword(Long user_idx);

  // change pw
  int changePw(int userAuto, String newPw, Long userIdx);
}
