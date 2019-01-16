package com.cryteck.universepath.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.poi.ss.usermodel.Row;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROUTE_INFO")
public class Route extends Graph {

	@Id
	@Column(name = "ROUTE_ID")
	Double routeId;

	public Route() {

	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PLANET_ORIGIN", referencedColumnName = "PLANET_NODE", insertable = false, updatable = false)
	Planet planetOriginBean;

	@Column(name = "PLANET_ORIGIN")
	String planetorigion;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PLANET_DESTINATATION", referencedColumnName = "PLANET_NODE", insertable = false, updatable = false)
	Planet planetDestinationBean;

	@Column(name = "PLANET_DESTINATATION")
	String planetDestination;

	@Column(name = "TRAFIC_DELAY")
	double trafficDelay;

	@Column(name = "DISTANCE")
	double distance;

	public Route(Row row) {

		setRouteId(new Double(row.getCell(0).getNumericCellValue()));
		setPlanetorigion(row.getCell(1).getStringCellValue());
		setPlanetDestination(row.getCell(2).getStringCellValue());
		setDistance(row.getCell(3).getNumericCellValue());

	}

	public Double getRouteId() {
		return routeId;
	}

	public void setRouteId(Double routeId) {
		this.routeId = routeId;
	}

	public Planet getPlanetOriginBean() {
		return planetOriginBean;
	}

	public void setPlanetOriginBean(Planet planetOriginBean) {
		this.planetOriginBean = planetOriginBean;
	}

	public String getPlanetorigion() {
		return planetorigion;
	}

	public void setPlanetorigion(String planetorigion) {
		this.planetorigion = planetorigion;
	}

	public Planet getPlanetDestinationBean() {
		return planetDestinationBean;
	}

	public void setPlanetDestinationBean(
			Planet planetDestinationBean) {
		this.planetDestinationBean = planetDestinationBean;
	}

	public String getPlanetDestination() {
		return planetDestination;
	}

	public void setPlanetDestination(String planetDestination) {
		this.planetDestination = planetDestination;
	}

	public double getTrafficDelay() {
		return trafficDelay;
	}

	public void setTrafficDelay(double trafficDelay) {
		this.trafficDelay = trafficDelay;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Double getWeight(boolean includeTraffic) {

		if (includeTraffic) {
			return trafficDelay + distance;

		} else {
			return distance;
		}
	}

}
