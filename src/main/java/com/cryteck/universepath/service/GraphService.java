package com.cryteck.universepath.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryteck.universepath.model.Distance;
import com.cryteck.universepath.model.Planet;
import com.cryteck.universepath.model.Route;
import com.cryteck.universepath.repository.PlanetRepository;
import com.cryteck.universepath.repository.RouteRepository;
import com.cryteck.universepath.util.CrytekBeanUtils;
import com.cryteck.universepath.util.DijsktraAlgoUtil;

@Service
public class GraphService {

	@Autowired
	PlanetRepository planetRepository;

	@Autowired
	RouteRepository routeRepository;

	@Autowired
	DataLoaderService dataLoaderService;

	/**
	 * api to get planet info based on node name
	 * 
	 * @param nodeName
	 * @return
	 */
	public List<Planet> getPlanet(Optional<String> nodeName) {
		if (nodeName.isPresent()) {
			List<Planet> planetsList = new ArrayList<Planet>();
			if (planetRepository.findById(nodeName.get()).isPresent())
				planetsList.add(planetRepository
						.findById(nodeName.get()).get());
			return planetsList;
		} else {
			return planetRepository.findAll();
		}
	}

	/**
	 * Api to get route info based on route id
	 * @param routeId
	 * @return
	 */
	public List<Route> getRoutes(Optional<Double> routeId) {

		if (routeId.isPresent()) {
			if (routeRepository.findById(routeId.get()).isPresent()) {
				List<Route> routeList = new ArrayList<Route>();
				routeList.add(routeRepository.findById(routeId.get())
						.get());
				return routeList;
			}

		} else {
			return routeRepository.findAll();
		}
		return null;

	}

	/**
	 * 
	 * Service to get the shortest path
	 * from point A to Point B
	 * 
	 * @param source
	 * @param destination
	 * @param includeTraffic
	 * @return
	 */
	public List<Distance> getShortestPath(String source,
			String destination, boolean includeTraffic) {

		HashMap<String, Distance> distanceSourceMap = DijsktraAlgoUtil
				.runDijstakra(planetRepository.count(), source,
						destination,
						dataLoaderService.getNodeNameCachedData(),
						dataLoaderService.getRouteCachedData(),
						includeTraffic);

		List<Distance> distanceList = CrytekBeanUtils
				.createDistanceList(distanceSourceMap, destination,
						dataLoaderService);
		return distanceList;

	}

}
