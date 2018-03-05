package com.kivi.framework.web.shiro.subject;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 无状态主题工厂
 *
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
	@Override
	public Subject createSubject(SubjectContext context) {
		//不创建session  
		context.setSessionCreationEnabled(false);
		return super.createSubject(context);
	}
}
