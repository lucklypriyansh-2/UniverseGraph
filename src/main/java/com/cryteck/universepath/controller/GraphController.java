package com.cryteck.universepath.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cryteck.universepath.model.Distance;
import com.cryteck.universepath.model.Planet;
import com.cryteck.universepath.model.Route;
import com.cryteck.universepath.service.GraphService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/Graph")
@ApiOperation(value = "Controller for GraphController ")
public class GraphController {

	@Autowired
	GraphService graphService;

	@RequestMapping(value = { "/Planet/{nodename}",
			"/Planet" }, method = RequestMethod.GET)
	public List<Planet> getPlanets(
			@PathVariable("nodename") Optional<String> nodeName) {
		return graphService.getPlanet(nodeName);

	}

	@GetMapping(value = { "/Route/{routeId}", "/Route" })
	public List<Route> getRoute(
			@PathVariable("routeId") Optional<Double> routeId) {
		return graphService.getRoutes(routeId);

	}

	@GetMapping(value = { "/Path/{source}/{destination}" })
	@ApiOperation(value = "gives minimum distance path from source to destination using dijskta", response = Distance.class)
	public List<Distance> getPath(
			@ApiParam("source node name  ") @PathVariable("source") String source,
			@ApiParam("destination node name  ") @PathVariable("destination") String destination,
			@RequestParam(value = "includeTraffic", defaultValue = "false") boolean includeTraffic) {

		return graphService.getShortestPath(source, destination,
				includeTraffic);

	}

}
