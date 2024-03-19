package com.taito.weather.model;

import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends AbstractModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "daytime_id")
	private DayTime daytime;

	@ManyToOne
	@JoinColumn(name = "weathertype_id")
	private WeatherType weather_type;

	@Column(name = "add_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@OrderBy("add_date DESC")
	@NotNull
	private LocalDateTime add_date;

	private Integer temp_morning;
	private Integer temp_middle;
	private Integer temp_evening;

	private Integer wtype_morning;
	private Integer wtype_middle;
	private Integer wtype_evening;

	@Column(columnDefinition = "TEXT")
	@NotBlank(message = "Info text cannot be blank")
	@Valid
//	@JsonProperty("description")
	private String info;

	@Override
	public String toString() {
		return "Event [daytime=" + daytime + ", weather_type=" + weather_type + ", add_date=" + add_date
				+ ", temp_morning=" + temp_morning + ", temp_middle=" + temp_middle + ", temp_evening=" + temp_evening
				+ ", wtype_morning=" + wtype_morning + ", wtype_middle=" + wtype_middle + ", wtype_evening="
				+ wtype_evening + ", info=" + info + "]";
	}
	
	
	

}
