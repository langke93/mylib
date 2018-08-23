package org.langke.design.pattern.strategy;

public class ModelDuck extends Duck {

	public ModelDuck(){
		flyBehavior = new FlyNoWay();
		quackBehavior = new Quack();
	}
	@Override
	void swim() {
		// TODO Auto-generated method stub

	}

	@Override
	void display() {
		System.out.println("I'm a model duck");

	}

}
