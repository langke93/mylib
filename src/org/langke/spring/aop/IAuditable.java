package org.langke.spring.aop;

import java.util.Date;

public interface IAuditable {
	void setLastModifiedDate(Date date);

	Date getLastModifiedDate();
}