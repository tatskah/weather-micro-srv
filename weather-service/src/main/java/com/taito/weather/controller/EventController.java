package com.taito.weather.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.taito.weather.dto.EventDto;
import com.taito.weather.exception.DataNotFoundException;
import com.taito.weather.model.Event;
import com.taito.weather.repository.EventRepository;
import com.taito.weather.service.EventService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("api/events")
@Slf4j
public class EventController {

	private final EventService eventService;
	private final EventRepository eventRepository;
	private final EventModelAssembler eventModelAssembler;

	@PostMapping
	public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
		EventDto savedEvent = eventService.createEvent(eventDto);
		return new ResponseEntity<EventDto>(savedEvent, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<EventDto>> getEvents() {
		List<EventDto> events = eventService.getEvents();
		return ResponseEntity.ok(events);

	}

	@GetMapping("{id}")
	public ResponseEntity<EventDto> getEventById(@PathVariable("id") Long id) {
		log.info("ID:"+id);
		EventDto event = eventService.getEventById(id);
		return ResponseEntity.ok(event);
	}

	@PutMapping("{id}")
	public ResponseEntity<EventDto> updateEvent(@PathVariable("id") Long id, @RequestBody EventDto eventDto) {
		EventDto eventUpdated = eventService.updateEvent(id, eventDto);

		return ResponseEntity.ok(eventUpdated);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
		eventService.deleteEventById(id);

		return ResponseEntity.ok("Event deleted succesfully by id " + id);
	}

	// ###################################### HATEOAS
	// #####################################
	@GetMapping("/ho")
	public ResponseEntity<CollectionModel<EntityModel<Event>>> getAllEventsHO() {
		List<EntityModel<Event>> events = StreamSupport.stream(eventRepository.findAll().spliterator(), false)
				.map(event -> EntityModel.of(event,
						linkTo(methodOn(EventController.class).getEventByIdWithHO(event.getId())).withSelfRel(),
						linkTo(methodOn(EventController.class).getAllEventsHO()).withSelfRel()))
				.collect(Collectors.toList());

		return ResponseEntity.ok(CollectionModel.of(events));
	}

	@GetMapping("ho/{id}")
	EntityModel<Event> getEventByIdWithHO(@PathVariable("id") Long id) {
		log.info("ID:"+id);
		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Data not found by id: " + id));
		return eventModelAssembler.toModel(event);

//		return eventRepository.findById(id)
//			.map(event -> EntityModel.of(event,
//						linkTo(methodOn(EventController.class).getEventByIdWithHO(event.getId())).withSelfRel(),
//						linkTo(methodOn(EventController.class).getAllEventsHO()).withRel("events")))
//						.map(ResponseEntity::ok)
//						.orElse(ResponseEntity.notFound().build());

	}

	@PostMapping("ho")
	ResponseEntity<EntityModel<Event>> addEvent(@RequestBody @Valid Event event) {
		Event eventSaved = eventRepository.save(event);

//		UriComponents components = UriComponentsBuilder
//				.fromUriString("api/events/ho/{id}")
//				.encode()
//				.build();
//		
//		URI uri = components.expand(event.getId()).toUri();
//		return ResponseEntity.created(uri).body(eventSaved);

		return ResponseEntity.status(HttpStatus.CREATED).body(eventModelAssembler.toModel(eventSaved));
	}

	@PutMapping("ho/{id}")
	public ResponseEntity<EntityModel<Event>> updateEventHo(@PathVariable("id") Long id,
			@RequestBody Event eventToSave) {
		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Data not found by id: " + id));
		event.setAdd_date(eventToSave.getAdd_date());
		event.setDaytime(eventToSave.getDaytime());
		event.setWeather_type(eventToSave.getWeather_type());
		event.setTemp_morning(eventToSave.getTemp_morning());
		event.setTemp_middle(eventToSave.getTemp_middle());
		event.setTemp_evening(eventToSave.getTemp_evening());
		event.setWtype_morning(eventToSave.getWtype_morning());
		event.setWtype_middle(eventToSave.getWtype_middle());
		event.setWtype_evening(eventToSave.getWtype_evening());
		event.setInfo(eventToSave.getInfo());

		Event eventSaved = eventRepository.save(event);

		return ResponseEntity.status(HttpStatus.CREATED).body(eventModelAssembler.toModel(eventSaved));
	}

	@DeleteMapping("ho/{id}")
	public ResponseEntity<String> deleteEventHO(@PathVariable Long id) {
		eventService.deleteEventById(id);
		return ResponseEntity.ok("Event deleted by id: " + id);
	}

	@GetMapping("ho/next/{id}")
	EntityModel<Event> getNextEvent(@PathVariable("id") Long id) {
		Event event = eventRepository.getNextEvent(id);
		return eventModelAssembler.toModel(event);
	}

	@GetMapping("ho/prev/{id}")
	EntityModel<Event> getPrevEvent(@PathVariable("id") Long id) {
		Event event = eventRepository.getPrevEvent(id);
		return eventModelAssembler.toModel(event);
	}

}
