package com.taito.weather.mapper;

import com.taito.weather.dto.EventDto;
import com.taito.weather.model.Event;

public class EventMapper {

	public static EventDto mapToEventDto(Event event) {
		return new EventDto(
				event.getId(),
				event.getDaytime(),
				event.getWeather_type(),
				event.getAdd_date(),
				event.getTemp_morning(),
				event.getTemp_middle(),
				event.getTemp_evening(),
				event.getWtype_morning(),
				event.getWtype_middle(),
				event.getWtype_evening(),
				event.getInfo()				
		);
	}
	
	public static Event mapToEvent(EventDto eventDot) {
		return new Event(
				eventDot.getId(),
				eventDot.getDaytime(),
				eventDot.getWeather_type(),
				eventDot.getAdd_date(),
				eventDot.getTemp_morning(),
				eventDot.getTemp_middle(),
				eventDot.getTemp_evening(),
				eventDot.getWtype_morning(),
				eventDot.getWtype_middle(),
				eventDot.getWtype_evening(),
				eventDot.getInfo()
		);
	}

}
