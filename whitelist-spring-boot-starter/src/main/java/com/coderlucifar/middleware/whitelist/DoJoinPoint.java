package com.coderlucifar.middleware.whitelist;


import com.alibaba.fastjson.JSON;
import com.coderlucifar.middleware.whitelist.annotation.DoWhiteList;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.Method;

@Component
@Aspect // 定义切面类
public class DoJoinPoint {

    private Logger log = LoggerFactory.getLogger(DoJoinPoint.class);

    @Resource
    private String whiteListConfig;

    /**
     * 自定义切入点
     */
    @Pointcut("@annotation(com.coderlucifar.middleware.whitelist.annotation.DoWhiteList)")
    public void aopPoint() {
    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable{
        // 获取内容
        Method method = getMethod(jp);
        DoWhiteList whiteList = method.getAnnotation(DoWhiteList.class);

        // 获取字段值
        String keyValue = getFieldValue(whiteList.key(), jp.getArgs());
        log.info("middleware whitelist handler method: {} value: {}", method.getName(), keyValue);
        if (keyValue == null || "".equals(keyValue)) return jp.proceed();

        String[] securityUsers = whiteListConfig.split(",");

        // 白名单过滤
        for (String user : securityUsers) {
            if (keyValue.equals(user)) {
                return jp.proceed();
            }
        }

        // 拦截
        return returnObject(whiteList, method);
    }

    private Object returnObject(DoWhiteList whiteList, Method method) throws InstantiationException, IllegalAccessException {
        // 获取方法返回类型
        Class<?> returnType = method.getReturnType();
        // 获取白名单注解定义的拦截提示消息
        String returnJson = whiteList.returnJson();
        if ("".equals(returnJson)) {
            // 如果没有定义提示消息默认返回
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    private String getFieldValue(String field, Object[] args) {
        String fieldValue = null;
        for (Object arg : args) {
            try {
                if (fieldValue == null || "".equals(fieldValue)) {
                    fieldValue = BeanUtils.getProperty(arg, field);
                } else {
                    break;
                }
            } catch (Exception ex) {
                if(args.length == 1) return args[0].toString();
            }
        }
        return fieldValue;
    }

    private Method getMethod(ProceedingJoinPoint jp) throws NoSuchMethodException {
        //
        Signature signature = jp.getSignature();
        //
        MethodSignature methodSignature = (MethodSignature) signature;
        //
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

}
