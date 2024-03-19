package com.taito.weather.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taito.weather.model.DayTime;
import com.taito.weather.model.WeatherType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

	private Long id;

	private DayTime daytime;

	private WeatherType weather_type;

	private LocalDateTime add_date;

	private Integer temp_morning;
	private Integer temp_middle;
	private Integer temp_evening;
	
	private Integer wtype_morning;
	private Integer wtype_middle;
	private Integer wtype_evening;

	@NotBlank(message = "Info can't be blank")
	@Valid
//	@JsonProperty("description")
	private String info;

}
