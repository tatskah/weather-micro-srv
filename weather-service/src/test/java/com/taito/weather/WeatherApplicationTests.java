package com.taito.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.Console;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.taito.weather.dto.EventDto;
import com.taito.weather.model.Event;
import com.taito.weather.repository.EventRepository;
import com.taito.weather.service.EventService;

@SpringBootTest
class WeatherApplicationTests {

	@MockBean
	private EventRepository eventRepository;
	
	@Autowired
	private EventService eventService;
	
	@Test
	public void mockTestEventFindById() {
		when(eventRepository.findById(121212124L)).thenReturn(Optional.of(new Event()));
		
		EventDto event = eventService.getEventById(121212124L);
	
		assertNotNull(event);
		
	}
	
	
	@Test
	public void testJUnitEventSaveEvent() {
		
		Event event = new Event();
		
		event.setId(111L);
		event.setInfo("Testi info");
		
		
		Event saved = eventRepository.save(event);
		
		Event retrieved = eventRepository.findById(saved.getId()).orElse(null);
		
		assertEquals(event.getInfo(), retrieved.getInfo());
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	void contextLoads() {
	}

}
