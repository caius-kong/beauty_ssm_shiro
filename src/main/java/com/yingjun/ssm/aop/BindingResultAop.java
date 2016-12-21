package com.yingjun.ssm.aop;

import com.yingjun.ssm.dto.BaseResult;
import com.yingjun.ssm.utils.Helper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 采用AOP的方式处理@Valid 参数验证。
 */
@Component
@Aspect
public class BindingResultAop {
    private static final Logger LOG = LoggerFactory.getLogger(BindingResultAop.class);

    @Pointcut("execution(* com.yingjun.ssm.web.*.*(..))")
    public void bindingResultPointcut(){
        //该方法的内容不重要，该方法的本身只是个标识，供@Pointcut注解依附
    }

    /**
     * Aspect = Advice + Pointcut
     * Advice: @Around
     * Pointcut: execution(* com.yingjun.ssm.web.*.*(..))
     */
    @Around("bindingResultPointcut()")
    public Object aroundAdvice(ProceedingJoinPoint jp) throws Throwable{
        System.out.println("--->BindingResultAop start...");
        LOG.info("before " + Helper.getFullMethod(jp) + "invoking!");

        // 遍历参数，找到BindingResult，判断是否hasError
        BindingResult bindingResult = null;
        for(Object arg: jp.getArgs()){
            if(arg instanceof BindingResult){
                bindingResult = (BindingResult) arg;
            }
        }
        if(bindingResult != null){
            if(bindingResult.hasErrors()){
                LOG.info("--->bindingResult hasError!");
                String errorInfo="["+bindingResult.getFieldError().getField()+"]"+bindingResult.getFieldError().getDefaultMessage();
                String returnType = jp.getSignature().toString().split("\\s")[0];
                if(returnType.equals("BaseResult")) {
                    // 如果是返回BaseResult对象，则返回包含错误信息的BaseResult
                    return new BaseResult(false, errorInfo);
                } else {
                    // 如果是返回String，则重定向到请求的url，并携带重定向参数
                    String requestURI = getRequestURI();
                    for(Object arg: jp.getArgs()){
                        if(arg instanceof RedirectAttributes){
                            RedirectAttributes redirectAttributes = (RedirectAttributes) arg;
                            redirectAttributes.addFlashAttribute("msg", errorInfo); // addAttribute添加的参数会附加在url中，addFlashAttribute不会(闪存，自动传播到当前请求的"输出"FlashMap)
                        }
                    }
                    return "redirect:"+requestURI;
                }
            }
        }

        // 执行目标方法
        return jp.proceed();
    }

    private String getRequestURI(){
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        return request.getRequestURI();
    }
}
