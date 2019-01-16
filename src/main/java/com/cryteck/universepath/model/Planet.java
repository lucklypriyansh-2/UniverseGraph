package com.cryteck.universepath.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PLANET_INFO")
public class Planet extends Graph {

	@Id
	@Column(name = "PLANET_NODE")
	String planetNode;

	@Column(name = "PLANET_NAME")
	String planetName;

	public Planet() {

	}

	public Planet(Row row) {

		setPlanetNode(row.getCell(0).getStringCellValue());
		setPlanetName(row.getCell(1).getStringCellValue());

	}

	public String getPlanetNode() {
		return planetNode;
	}

	public void setPlanetNode(String planetNode) {
		this.planetNode = planetNode;
	}

	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}
}
