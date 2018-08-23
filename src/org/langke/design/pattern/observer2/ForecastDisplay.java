package org.langke.design.pattern.observer2;

import java.util.Observable;
import java.util.Observer;

import org.langke.design.pattern.observer.DisplayElement;

public class ForecastDisplay implements Observer  ,DisplayElement{

	private float currentPressure = 28.93f;
	private float lastPressure;
	public ForecastDisplay(Observable observable){
		observable.addObserver(this);
	}
	@Override
	public void display() {
		System.out.println("display lastPressure:"+lastPressure+" currentPressure"+currentPressure);
	}

	@Override
	public void update(Observable obs, Object arg) {
		if(obs instanceof WeatherData){
			WeatherData weatherData = (WeatherData)obs;
			lastPressure = currentPressure;
			currentPressure = weatherData.getPressure();
			display();
		}
	}

}
