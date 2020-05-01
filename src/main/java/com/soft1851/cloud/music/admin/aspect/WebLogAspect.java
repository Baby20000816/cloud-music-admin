package com.soft1851.cloud.music.admin.aspect;

import com.soft1851.cloud.music.admin.annotation.Mobile;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Guorc
 * @Description
 * @Date 2020/4/9
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class WebLogAspect {
    /**
     * ThreadLocal可以让我们拥有当前线程的变量，可以通过set()和get()来对每个局部变量进行操作
     * 不会和其他线程的局部变量进行冲突，实现了线程的数据隔离
     * 此处用它进行日志信息在各个方法间的共享
     */
    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    /**
     * 定义切点,横切controller包下所有公有方法
     * 到时候对Excel导出的controller进行切
     */
    @Pointcut("execution(public * com.soft1851.music.admin.controller..*.*(..))")

    public void webLog() {

    }

    /**
     * 前置增强
     *
     * @param joinpoint
     * @param mobile
     */
    @Before(value = "webLog() && @annotation(mobile)")
    public void doBefore(JoinPoint joinpoint, Mobile mobile) {
        //接受请求，取得请求的request对象
        RequestAttributes at = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) at;
        //以下通过连接点和注解获取到相关信息
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        log.info("请求URI：" + request.getRequestURI());
        log.info("请求URL：" + request.getRequestURL());
        log.info("请求头的User-Agent：" + request.getHeader("User-Agent"));
        log.info("请求方法：" + request.getMethod());
        log.info("请求地址：" + request.getRemoteAddr());
        log.info("连接点对象通过反射获得类名和方法名" + joinpoint.getSignature().getDeclaringTypeName() + "."
                + joinpoint.getSignature().getName());
        log.info("AOP拦截获得参数：" + Arrays.toString(joinpoint.getArgs()));
        //定义一个map用来记录日志信息，并将其put入threadLocal
        Map<String, Object> map = new HashMap<>(16);
        map.put("uri", request.getRequestURI());
        map.put("url", request.getRequestURL());
        map.put("user-agent", request.getHeader("User-Agent"));
        map.put("request-method", request.getMethod());
        map.put("remote-address", request.getRemoteAddr());
        map.put("class-method", joinpoint.getSignature().getDeclaringTypeName() + "."
                + joinpoint.getSignature().getName());
        map.put("arguments", Arrays.toString(joinpoint.getArgs()));
        threadLocal.set(map);


//        log.info("请求方法名：" + controllerWebLog.name());
//        log.info("是否需要持久化该条日志：" + controllerWebLog.isSaved());
//        log.info("AOP拦截获得参数：" + Arrays.toString(joinpoint.getArgs()));
    }

    /**
     * 执行成功处理
     *
     * @param mobile
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(value = "webLog()&& @annotation(mobile)", returning = "ret")
    public void doAfterReturning(Mobile mobile, Object ret) throws Throwable {
        //从当前线程变量取出数据
        Map<String, Object> threadInfo = threadLocal.get();
        //将请求的目标方法getHello()的执行的返回结果存入线程对象
        threadInfo.put("result", ret);
        //处理完请求，返回内容
        log.info("RESPONSE：" + ret);
    }

    @Around(value = "webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //得到开始时间
        long startTime = System.currentTimeMillis();
        //执行连接点的目标方法getHello()
        Object ob = proceedingJoinPoint.proceed();
        Map<String, Object> threadInfo = threadLocal.get();
        //计算出方法名的真实执行时间，可以在目标方法中加入线程休眠体会结果
        Long takeTime = System.currentTimeMillis() - startTime;
        //存入线程变量
//        threadInfo.put("takeTime", takeTime);
        log.info("耗时：" + takeTime);
        threadLocal.set(threadInfo);
        return ob;
    }

    @AfterThrowing(value = "webLog()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        //异常信息
        log.error("{}接口调用异常，异常信息{}", request.getRequestURI(), throwable);
    }
}
