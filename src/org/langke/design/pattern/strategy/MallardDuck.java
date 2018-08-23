package org.langke.design.pattern.strategy;

public class MallardDuck extends Duck {

	public MallardDuck(){
		quackBehavior = new Quack();
		flyBehavior = new FlyWithWings();
	}
	@Override
	void swim() {
		// TODO Auto-generated method stub

	}

	@Override
	void display() {
		System.out.println("I'm a real Mallard duck");

	}

}
