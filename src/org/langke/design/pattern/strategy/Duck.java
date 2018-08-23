package org.langke.design.pattern.strategy;
/**
 * 策略模式：定义了算法族，分别封装起来，让它们之间可以互相替换，此模式让算法的变化独立于使用算法的客户。
 * @author langke
 *
 */
public abstract class Duck {

	public FlyBehavior flyBehavior;
	public QuackBehavior quackBehavior;
	
	public void setFlyBehavior(FlyBehavior flyBehavior) {
		this.flyBehavior = flyBehavior;
	}

	public void setQuackBehavior(QuackBehavior quackBehavior) {
		this.quackBehavior = quackBehavior;
	}

	public void performQuack(){
		quackBehavior.quack();
	}
	
	abstract void  swim();
	abstract void display();
	
	public void performFly(){
		flyBehavior.fly();
	}
}
