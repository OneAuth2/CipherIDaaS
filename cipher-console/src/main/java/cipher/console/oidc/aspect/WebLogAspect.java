package cipher.console.oidc.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @Author: zt
 * @Date: 2018/10/31 16:31
 */
@Aspect
@Component
public class WebLogAspect {


    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * cipher.console.oidc.controller..*.*(..))")
    public void log() {
    }


    @Around("log()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        LOGGER.info("Enter the :"+methodName);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object obj = pjp.proceed(pjp.getArgs());
        stopWatch.stop();
        long cost = stopWatch.getTotalTimeMillis();
        LOGGER.info("Leave from the:{},耗时:{} ms",methodName,cost);
        return obj;
    }
}
