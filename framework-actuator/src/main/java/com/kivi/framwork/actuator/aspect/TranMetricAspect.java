package com.kivi.framwork.actuator.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

import com.kivi.framework.util.kit.StrKit;
import com.kivi.framwork.actuator.annotation.TranMetric;
import com.kivi.framwork.actuator.annotation.enums.MetircType;

/**
 * 日志记录
 *
 */
@Aspect
@Component
public class TranMetricAspect {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private ThreadLocal<TranMetric> tranMetrics = new ThreadLocal<TranMetric>();

	private final CounterService counterService;

	@Autowired
	public TranMetricAspect(CounterService counterService) {
		this.counterService = counterService;
	}

	@Pointcut(value = "@annotation(com.kivi.framwork.actuator.annotation.TranMetric)")
	public void cutMethod() {
	}

	@Around("cutMethod()")
	public Object tranMetric(ProceedingJoinPoint point) throws Throwable {

		// 获取拦截的方法名
		Signature sig = point.getSignature();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature)) {
			throw new IllegalArgumentException("该注解只能用于.方法");
		}
		msig = (MethodSignature) sig;
		Object target = point.getTarget();

		log.trace("交易统计，方法：{}.{}", target.getClass().getName(), msig.getName());

		Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

		// 获取操作名称
		TranMetric annotation = currentMethod.getAnnotation(TranMetric.class);
		tranMetrics.set(annotation);
		beforeProcess(annotation);
		// 先执行业务
		Object result = point.proceed();
		afterProcess(annotation);

		return result;
	}

	@AfterThrowing(value = "cutMethod()", throwing = "ex")
	public void tranExceptionMetric(Exception ex) {
		TranMetric annotation = tranMetrics.get();
		throwingProcess(annotation);
	}

	/**
	 * 函数处理前的度量
	 * 
	 * @param annotation
	 */
	private void beforeProcess(TranMetric annotation) {
		MetircType[] types = annotation.type();

		for (MetircType type : types) {
			counter(annotation.value(), type);
		}
	}

	private void afterProcess(TranMetric annotation) {
		MetircType[] types = annotation.type();
		for (MetircType type : types) {
			if (MetircType.Processing.compareTo(type) == 0)
				counter(annotation.value(), MetircType.Done);
		}
	}

	private void throwingProcess(TranMetric annotation) {
		counter(annotation.value(), MetircType.Throwing);
	}

	private void counter(String name, MetircType type) {
		if (MetircType.Total.compareTo(type) == 0) {
			counterService.increment(StrKit.join(".", name, type.name()));
		} else if (MetircType.Queued.compareTo(type) == 0) {
			counterService.increment(StrKit.join(".", name, type.name()));
		} else if (MetircType.Processing.compareTo(type) == 0) {
			counterService.increment(StrKit.join(".", name, type.name()));
			counterService.decrement(StrKit.join(".", name, MetircType.Queued.name()));
		} else if (MetircType.Done.compareTo(type) == 0) {
			counterService.increment(StrKit.join(".", name, type.name()));
			counterService.decrement(StrKit.join(".", name, MetircType.Processing.name()));
		} else {
			counterService.increment(StrKit.join(".", name, type.name()));
		}

	}

}