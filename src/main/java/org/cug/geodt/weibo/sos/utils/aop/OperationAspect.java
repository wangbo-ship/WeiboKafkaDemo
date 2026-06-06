package org.cug.geodt.weibo.sos.utils.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.cug.geodt.weibo.sos.pojo.GeodtServiceLog;
import org.cug.geodt.weibo.sos.service.LogService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
/**
 * 操作记录 切面 环绕增强
 * 用来记录部分用户操作的记录
 * Before: 前置通知, 在方法执行之前执行
 * After: 后置通知, 在方法执行之后执行
 * AfterRunning: 返回通知, 在方法返回结果之后执行
 * AfterThrowing: 异常通知, 在方法抛出异常之后
 * Around: 环绕通知, 围绕着方法执行
 * @FileName operationLogAspect
 * @Author WJW
 * @Date 2023/10/10 11:01
 * @Description
 */
@Component
@Aspect
@Slf4j
public class OperationAspect {

    @Resource
    private LogService operationLogService;

    //匹配所有添加 了 operationLogInterceptor 自定义注解的类
    @Pointcut("@annotation(org.cug.geodt.weibo.sos.annotation.OperationInterceptor)")
    public void executeService() {

    }

    /**
     * 后置通知, 在方法执行之后执行
     * ProceedingJoinPoint 类 只能环绕增强使用，其他可以使用JoinPoint
     * @return
     */
    @Around("executeService()")
    public Object before(ProceedingJoinPoint joinPoint){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Object[] args = joinPoint.getArgs();
        // 获取
//        Account account = null;
        for (Object arg : args) {
            // 获取方法中当前登录用户信息
            int indexOF = StringUtils.indexOf(arg.toString(),"Account");
            if(indexOF != -1 ){
//                account =(Account) arg;
            }
        }

        Object object = null;
        try {
            //获取切点 方法的返回值
            object=joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

//        if(account == null || object == null){
//            log.info("operationLogAspect Around获取当前登录用户信息/返回值为null，不记录这条操作");
//            return  object;
//        }

        //获取用户的真实请求ip
        String url = request.getRequestURI();
        GeodtServiceLog geodtServiceLog = new GeodtServiceLog();
        geodtServiceLog.setRequestUri(request.getRequestURI());
        geodtServiceLog.setMethod(request.getMethod());
        geodtServiceLog.setProtocol(request.getProtocol());
        geodtServiceLog.setRemoteAddress(request.getRemoteAddr());
        geodtServiceLog.setRequestUrl(request.getRequestURL().toString());
        geodtServiceLog.setRemotePort(String.valueOf(request.getRemotePort()));
        geodtServiceLog.setRemoteUser(request.getRemoteUser());
        geodtServiceLog.setCreateTime(new Date());

//        BaseDataRespDTO baseDataRespDTO =(BaseDataRespDTO)(object);
//        operationLog.setIp(EViewMethod.getIpAddress(request));
//        operationLog.setAccountId(account.getId());
//        operationLog.setCreator(account.getName());
//        operationLog.setCreateTime(new Date());
//        operationLog.setUpdateTime(new Date());
//        //获取请求路径的中最后一个 / 的字符串 ,操作类型（insert,update,delete）
//        operationLog.setName(url.substring(url.lastIndexOf("/")+1));
//        operationLog.setAccountAction(url);
//        operationLog.setDetail(baseDataRespDTO.getMeassageInfo());
//        operationLog.setIsSuccess(baseDataRespDTO.getCode() == ReturnCodeConstant.SUCCESS );
//        operationLog.setDetail(baseDataRespDTO.getMeassageInfo());
//        operationLog.setoperationLogTerrace(EViewMethod.getDeviceType(request));
        //执行数据库新增
        operationLogService.save(geodtServiceLog);
        return  object;
    }
}