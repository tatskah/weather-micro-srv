package com.taito.weather.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.taito.weather.model.Event;

@Component
public class EventModelAssembler implements RepresentationModelAssembler<Event, EntityModel<Event>> {

	@Override
	public EntityModel<Event> toModel(Event event) {

		return EntityModel.of(event,
				linkTo(methodOn(EventController.class).getEventByIdWithHO(event.getId())).withSelfRel(),
				linkTo(methodOn(EventController.class).getAllEventsHO()).withRel("events"),
				linkTo(methodOn(EventController.class).deleteEventHO(event.getId())).withRel("delete"),
				linkTo(methodOn(EventController.class).getPrevEvent(event.getId())).withRel("prev"),
				linkTo(methodOn(EventController.class).getNextEvent(event.getId())).withRel("next"));
	}

}
