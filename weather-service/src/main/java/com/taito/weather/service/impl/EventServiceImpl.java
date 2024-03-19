package com.taito.weather.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.taito.weather.dto.EventDto;
import com.taito.weather.exception.DataNotFoundException;
import com.taito.weather.mapper.EventMapper;
import com.taito.weather.model.Event;
import com.taito.weather.repository.EventRepository;
import com.taito.weather.service.EventService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;

	@Override
	public EventDto createEvent(EventDto eventDto) {

		Event event = EventMapper.mapToEvent(eventDto);
		Event savedEvent = eventRepository.save(event);

		return EventMapper.mapToEventDto(savedEvent);
	}

	@Override
	public EventDto getEventById(Long id) {
		Event event = this.getEvent(id);
		
		return EventMapper.mapToEventDto(event);
	}

	@Override
	public List<EventDto> getEvents() {
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		List<Event> events = (List<Event>) eventRepository.findAll(sort);
		return events.stream().map((event) -> EventMapper.mapToEventDto(event)).collect(Collectors.toList());
	}

	@Override
	public EventDto updateEvent(Long id, EventDto eventDto) {
		Event event = this.getEvent(id);
		
		event.setDaytime(eventDto.getDaytime());
		event.setWeather_type(eventDto.getWeather_type());
		event.setTemp_evening(eventDto.getTemp_evening());
		event.setTemp_middle(eventDto.getTemp_middle());
		event.setTemp_morning(eventDto.getTemp_morning());
		event.setWtype_evening(eventDto.getWtype_evening());
		event.setWtype_middle(eventDto.getWtype_middle());
		event.setWtype_morning(eventDto.getWtype_morning());
		event.setInfo(eventDto.getInfo());
		
		Event savedEvent = eventRepository.save(event);
		return EventMapper.mapToEventDto(savedEvent);
	}

	@Override
	public void deleteEventById(Long id) {
		this.getEvent(id);
		
		eventRepository.deleteById(id);
	}

	private Event getEvent(Long id) {						
		Event event = eventRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Event not found by id: " + id));
		return event;
	}
	
}








