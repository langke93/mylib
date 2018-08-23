package org.langke.design.pattern.observer;

import org.langke.design.pattern.observer2.ForecastDisplay;

public class WeatherStation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
		CurrentConditionsDisplay currentConditionsDisplay = 
				new CurrentConditionsDisplay(weatherData);
		weatherData.setMeasurements(80, 65, 39.4f);
		weatherData.setMeasurements(82, 40, 29.2f);
		weatherData.setMeasurements(78, 80, 29.2f);
		System.out.println("-------------");
		org.langke.design.pattern.observer2.WeatherData weatherData2 = new org.langke.design.pattern.observer2.WeatherData();
		org.langke.design.pattern.observer2.CurrentConditionsDisplay currentConditionsDisplay2 = new org.langke.design.pattern.observer2.CurrentConditionsDisplay(weatherData2);
		ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData2);
		weatherData2.setMeasurements(1, 2, 3);
		
	}

}
