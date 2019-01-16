package com.cryteck.universepath.model;

import java.util.List;

import lombok.Data;

@Data
public class PathResponse {

	Double total= new Double(0);
	List<Distance> pathList;
	

	public void addTotal(Double distance) {

		if (Double.compare(distance, Double.MAX_VALUE) < 0)
			total += distance;
	}
}
