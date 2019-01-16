package com.cryteck.universepath.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.cryteck.universepath.exception.UniverseModuleException;
import com.cryteck.universepath.model.Planet;
import com.cryteck.universepath.model.Route;
import com.cryteck.universepath.repository.PlanetRepository;
import com.cryteck.universepath.repository.RouteRepository;
import com.cryteck.universepath.util.CrytekBeanUtils;

@Service
public class DataLoaderService {

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	PlanetRepository planetRespository;

	@Autowired
	RouteRepository routeRepository;

	/**
	 * Initialize from csv and create database entries
	 */
	@PostConstruct
	public void init() {
		Resource resource = resourceLoader
				.getResource("classpath:" + "/data/data.xlsx");

		try {

			Workbook xssfWorkbook = new XSSFWorkbook(
					resource.getInputStream());

			List<Planet> planetList = CrytekBeanUtils.createBeanList(
					xssfWorkbook.getSheetAt(0), Planet.class, true);

			List<Route> routesList = CrytekBeanUtils.createBeanList(
					xssfWorkbook.getSheetAt(1), Route.class, true);

			List<Route> routesListWeighted = CrytekBeanUtils
					.createBeanList(xssfWorkbook.getSheetAt(2),
							Route.class, true);

			mergeRoutes(routesList, routesListWeighted);

			planetRespository.saveAll(planetList);
			routeRepository.saveAll(routesList);

		} catch (Exception e) {

			throw new UniverseModuleException(e,
					"error in read workbook");
		}

	}

	/**
	 * Merge destination route list from source route list using route id as
	 * identified and create a single routebean with traffic and weight data
	 * 
	 * @param destination
	 * @param source
	 */
	private void mergeRoutes(List<Route> destination,
			List<Route> source) {

		Map<Double, Route> routeidMap = source.parallelStream()
				.collect(Collectors.toMap(Route::getRouteId,
						Function.identity()));

		destination.parallelStream().forEach(des -> {

			des.setTrafficDelay(
					routeidMap.get(des.getRouteId()).getDistance());
		});

	}

	/**
	 * Cached map for planet node and planet name
	 * 
	 * @return
	 */
	@Cacheable("planetnodename")
	public Map<String, Planet> getNodeNameCachedData() {
		Map<String, Planet> planetsNodeNameMap = planetRespository
				.findAll().parallelStream().collect(Collectors.toMap(
						Planet::getPlanetNode, Function.identity()));

		return planetsNodeNameMap;

	}

	/**
	 * Cached map for planet name and List of routes
	 * 
	 * @return
	 */

	@Cacheable("planetroutename")
	public Map<String, List<Route>> getRouteCachedData() {
		Map<String, List<Route>> planetOrginRoutesMap = routeRepository
				.findAll().parallelStream().collect(Collectors
						.groupingBy(Route::getPlanetorigion));
		return planetOrginRoutesMap;

	}

}
