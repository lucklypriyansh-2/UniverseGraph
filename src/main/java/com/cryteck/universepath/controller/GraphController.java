package com.cryteck.universepath.controller;

import java.util.HashMap;
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

@RestController
@RequestMapping("/Graph")
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
	public List< Distance> getPath(
			@PathVariable("source") String source,
			@PathVariable("destination") String destination,
			@RequestParam(value = "includeTraffic", defaultValue = "false") boolean includeTraffic) {

		return graphService.getShortestPath(source, destination,
				includeTraffic);

	}

}
