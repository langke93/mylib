package org.langke.design.pattern.strategy;

public class FlyNoWay implements FlyBehavior {

	@Override
	public void fly() {
		System.out.println("no fly");

	}

}
