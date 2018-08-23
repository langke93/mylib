package org.langke.design.pattern.observer2;

import java.util.Observable;
import java.util.Observer;

import org.langke.design.pattern.observer.DisplayElement;

public class CurrentConditionsDisplay implements Observer,DisplayElement{

	Observable observable;
	private float temperature;
	private float humidity;
	
	public CurrentConditionsDisplay(Observable observable){
		this.observable = observable;
		observable.addObserver(this);
	}
	
	@Override
	public void display() {
		System.out.println("Current conditons:"+temperature+"F degress and "+humidity+"% humidity");
	}

	@Override
	public void update(Observable obs, Object arg) {
		if(obs instanceof WeatherData){
			WeatherData weatherData = (WeatherData)obs;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();
			display();
		}
	}

}
