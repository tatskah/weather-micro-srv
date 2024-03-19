package com.taito.weather.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.taito.weather.model.Event;

public interface EventRepository extends CrudRepository<Event, Long> {
	Iterable<Event> findAll(Sort sort);
	
	@Query("SELECT e FROM Event e WHERE e.id > :curr_id ORDER by e.id ASC LIMIT 1")
	public Event getNextEvent(@Param("curr_id") Long id);
	
	@Query("SELECT e FROM Event e WHERE e.id < :curr_id ORDER by e.id DESC LIMIT 1")
	public Event getPrevEvent(@Param("curr_id") Long id);
	
}

