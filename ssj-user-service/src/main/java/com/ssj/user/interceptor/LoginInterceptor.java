package com.ssj.user.interceptor;

import com.ssj.user.common.CommonConst;
import com.ssj.user.common.CommonResponse;
import com.ssj.user.enums.ResponseCodeEnum;
import com.ssj.user.util.RedisJedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisJedisUtil redisJedisUtil;

    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(CommonConst.TOKEN);
        if(StringUtils.isEmpty(token) || StringUtils.isBlank(redisJedisUtil.getString(token))) {
            log.info("uri:{},token:{},未登录", request.getRequestURI(), token);
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(CommonResponse.fail(
                    ResponseCodeEnum.NOT_LOGINED.getCode(), ResponseCodeEnum.NOT_LOGINED.getMsg()).toString());
            return false;
        }
        return true;
    }

    //目标方法执行之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
