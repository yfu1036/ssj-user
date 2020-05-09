package com.ssj.user.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@Aspect
@Slf4j
public class ControllerLogAspect {

    //定义切入点,拦截controller包其子包下的所有类的所有方法
    @Pointcut("execution(public * com.ssj.user.controller..*.*(..))")
    public void excuteService() {

    }

    @Around("execution(public * com.ssj.user.controller..*.*(..))")
    public Object processLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //1 执行原方法前
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取方法名称
        String methodName = method.getName();
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //请求uri
        String uri = request.getRequestURI();
        //请求参数
        Map<String, Object> rqsParams = null;
        //获取参数名称
        LocalVariableTableParameterNameDiscoverer paramNames = new LocalVariableTableParameterNameDiscoverer();
        String[] params = paramNames.getParameterNames(method);
        //获取参数
        Object[] args = joinPoint.getArgs();
        //过滤掉request和response,不能序列化
        List<Object> filteredArgs = Arrays.stream(args)
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(filteredArgs)) {
            //拼接请求参数
            /*rqsParams = IntStream.range(0, filteredArgs.size())
                    .boxed()
                    .collect(Collectors.toMap(j -> params[j], j -> filteredArgs.get(j)));*/
        }
        log.info("请求uri:{},method:{},入参:{}", uri, methodName, JSONObject.toJSONString(filteredArgs));
        long startTime = System.currentTimeMillis();

        //2 执行原方法
        Object rspObj = joinPoint.proceed(args);

        //3 执行原方法后
        //打印返回值
        log.info("请求uri:{},method:{},返回:{}", uri, methodName, rspObj);
        //打印耗时的信息
        long endTime = System.currentTimeMillis();
        log.info("请求uri:{},method:{},耗时:{}ms", uri, methodName, endTime-startTime);
        return rspObj;
    }

}
