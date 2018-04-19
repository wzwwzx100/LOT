package com.mogu.LOT.api.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 系统服务组件Aspect切面Bean
 */
@Component
@Aspect
public class AopAspect {
	private final static Log logger = LogFactory.getLog(AopAspect.class);

	// 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(public * com.mogu.GEMAKER.service.impl.*.*(..))")
	public void aspect() {
	}


	//配置增加切入点
	//add
	@Pointcut("execution(* com.mogu.GEMAKER.service.impl.*.add*(..))")
	public void add() {}

	//配置修改切入点
	//upd
	@Pointcut("execution(* com.mogu.GEMAKER.service.impl.*.update*(..))")
	public void update() {}

	//配置删除切入点
	//del
	@Pointcut("execution(* com.mogu.GEMAKER.service.impl.*.delete*(..))")
	public void delete() {}




	@AfterReturning(value = "add()",argNames = "joinPoint,rtv",returning = "rtv")
	public void add(JoinPoint joinPoint, Object rtv){
//		createLog(joinPoint.getArgs(), "新增");
	}

	@AfterReturning(value = "update()",argNames = "joinPoint,rtv",returning = "rtv")
	public void update(JoinPoint joinPoint, Object rtv){
//		createLog(joinPoint.getArgs(), "修改");
	}

	@AfterReturning(value = "delete()",argNames = "joinPoint,rtv",returning = "rtv")
	public void delete(JoinPoint joinPoint, Object rtv){
//		createLog(joinPoint.getArgs(), "删除");
	}






	private static String getRequest(){
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest().getRequestURI();
	}


	private static String getIp(){
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest().getRemoteAddr();
	}


	/*
	 * 配置前置通知,使用在方法aspect()上注册的切入点 同时接受JoinPoint切入点对象,可以没有该参数
	 */
	@Before("aspect()")
	public void before(JoinPoint joinPoint) {
		if (logger.isInfoEnabled()) {
			logger.info("before " + joinPoint);
		}
	}

	// 配置后置通知,使用在方法aspect()上注册的切入点
	@After("aspect()")
	public void after(JoinPoint joinPoint) {
		if (logger.isInfoEnabled()) {
			logger.info("after " + joinPoint);
		}
	}

	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	@AfterReturning("aspect()")
	public void afterReturn(JoinPoint joinPoint) {
		if (logger.isInfoEnabled()) {
			logger.info("afterReturn " + joinPoint);
		}
	}

	// 配置抛出异常后通知,使用在方法aspect()上注册的切入点
	@AfterThrowing(pointcut = "aspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex) {
		if (logger.isWarnEnabled()) {
			logger.warn("afterThrow " + joinPoint + "\t" + ex.getMessage());
		}
	}

}