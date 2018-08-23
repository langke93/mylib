package org.langke.design.pattern.observer;

/**
 * 观察者模式：定义了对象的一对多依赖，这样一来，当一个对象改变状态时，它的所有依赖者都会收到通知并自动更新。
 * @author langke
 *
 */
public interface Observer {

	public void update(float temp,float humidity,float pressure);
}
