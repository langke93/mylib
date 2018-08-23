package org.langke.design.pattern.strategy;

public class MuteQuack implements QuackBehavior {

	@Override
	public void quack() {
		System.out.println("no quack");

	}

}
