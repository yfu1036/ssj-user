package com.ssj.user.service;

public interface TokenService {
    /**
     * 根据token获取用户id
     * @param token
     * @return
     */
    String getUserIdByToken(String token);
}
