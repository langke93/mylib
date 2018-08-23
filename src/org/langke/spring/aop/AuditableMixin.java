package org.langke.spring.aop;

import java.util.Date;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;

public class AuditableMixin implements IAuditable, IntroductionInterceptor {
	private Date lastModifiedDate;

	public Object invoke(MethodInvocation m) throws Throwable {
		// TODO Add your codes here
		if (implementsInterface(m.getMethod().getDeclaringClass())) {
			return m.getMethod().invoke(this, m.getArguments());
			// invoke introduced mthod,here is IAuditable
		} else {
			return m.proceed(); // delegate other method
		}
	}

	public Date getLastModifiedDate() {
		// TODO Add your codes here
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date date) {
		// TODO Add your codes here
		lastModifiedDate = date;
	}

	public boolean implementsInterface(Class cls) {
		// TODO Add your codes here
		return cls.isAssignableFrom(IAuditable.class);
	}

}
