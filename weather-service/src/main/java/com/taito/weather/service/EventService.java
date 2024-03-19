package com.taito.weather.service;

import java.util.List;

import com.taito.weather.dto.EventDto;

public interface EventService {
	
	EventDto createEvent(EventDto eventDto);
	
	EventDto getEventById(Long id);
	
	List<EventDto> getEvents();
	
	EventDto  updateEvent(Long id, EventDto eventDto);
	
	void deleteEventById(Long id);
	
}
