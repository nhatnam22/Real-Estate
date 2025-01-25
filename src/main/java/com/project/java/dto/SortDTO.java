package com.project.java.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SortDTO {
	private String field;
	private String Direction;
	private Date date;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDirection() {
		return Direction;
	}
	public void setDirection(String direction) {
		Direction = direction;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
