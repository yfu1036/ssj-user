package com.ssj.user.filter;

import com.ssj.user.common.CommonConst;
import com.ssj.user.common.CommonResponse;
import com.ssj.user.enums.ResponseCodeEnum;
import com.ssj.user.util.RedisJedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
@Component
public class LoginCheckFilter extends GenericFilterBean {

    @Value(("${http.login.check.switch}"))
    private String loginSwitch;

    @Value(("${http.login.check.release.uri}"))
    private String releaseUri;

    @Autowired
    private RedisJedisUtil redisJedisUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        // 登录校验开关打开，并且不是放行接口
        if(CommonConst.SWITCH_ON.equals(loginSwitch) && !Pattern.matches(releaseUri, uri)) {
            String token = request.getHeader(CommonConst.TOKEN);
            if(StringUtils.isBlank(token) || StringUtils.isBlank(redisJedisUtil.getString(token))) {
                log.error("uri:{},token:{},未登录", uri, token);
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(CommonResponse.fail(
                        ResponseCodeEnum.NOT_LOGINED.getCode(), ResponseCodeEnum.NOT_LOGINED.getMsg()).toString());
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
