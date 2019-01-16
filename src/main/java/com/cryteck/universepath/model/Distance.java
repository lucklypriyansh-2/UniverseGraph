package com.cryteck.universepath.model;

import lombok.Data;

@Data
public class Distance {

	Double distance;
	String source;
	String currentnode;
	String sourceAlias;
	String currentNodeAlias;

	public Distance(Double distance, String source) {
		this.distance = distance;
		this.source = source;
	}

	public Distance(Double distance, String source,String currentnode) {
		this.distance = distance;
		this.source = source;
		this.currentnode=currentnode;
	}
	
	
}
