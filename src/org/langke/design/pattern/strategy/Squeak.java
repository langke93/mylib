package org.langke.design.pattern.strategy;

public class Squeak implements QuackBehavior {

	@Override
	public void quack() {
		System.out.println("squeak");
	}

}
