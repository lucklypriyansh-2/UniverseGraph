package com.cryteck.universepath.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cryteck.universepath.model.Distance;
import com.cryteck.universepath.model.Planet;
import com.cryteck.universepath.model.Route;
import com.zaxxer.hikari.util.SuspendResumeLock;

public class DijsktraAlgoUtil {

	/**
	 * 
	 * Core dijsaktra algorithm to get the single source shortest path
	 * 
	 * @param vertex
	 * @param source
	 * @param destination
	 * @param routesList
	 * @param planetsList
	 * @return
	 */
	public static HashMap<String, Distance> runDijstakra(
			double vertex, String source, String destination,
			Map<String, Planet> planetNodeNameMap,
			Map<String, List<Route>> planetRouteMap,
			boolean includeTraffic) {

		// included edges in path map
		HashMap<String, Boolean> includedInPath = new HashMap<>();
		//distance map the return distance discoverd
		HashMap<String, Distance> distanceMap = new HashMap<>();

		InitlializeVariables(source, planetNodeNameMap,
				includedInPath, distanceMap);

		for (int i = 0; i < vertex; i++) {

			// minimum distance source node in each iteration
			String mindistanceSrcNode = findMinDistanceNode(
					distanceMap, includedInPath);
			includedInPath.put(mindistanceSrcNode, true);

			if (mindistanceSrcNode.equalsIgnoreCase(destination))
				return distanceMap;

			System.out.println(mindistanceSrcNode);

			if (planetRouteMap.get(mindistanceSrcNode) == null)
				continue;
			for (Route destinationRoute : planetRouteMap
					.get(mindistanceSrcNode)) {
				if (checkvalidcondition(destinationRoute,
						mindistanceSrcNode, distanceMap,
						includedInPath)) {

					updateDistance(distanceMap, mindistanceSrcNode,
							destinationRoute, includeTraffic);

				}
			}

		}
		return distanceMap;

	}

	/**
	 * Method to initialize the varibales
	 * 
	 * @param source
	 * @param planetNodeNameMap
	 * @param includedInPath
	 * @param distanceMap
	 */
	private static void InitlializeVariables(String source,
			Map<String, Planet> planetNodeNameMap,
			HashMap<String, Boolean> includedInPath,
			HashMap<String, Distance> distanceMap) {
		planetNodeNameMap.entrySet().forEach(entry -> {
			includedInPath.put(entry.getValue().getPlanetNode(),
					false);
			distanceMap.put(entry.getValue().getPlanetNode(),
					new Distance(Double.MAX_VALUE, null));
		});

		distanceMap.put(source, new Distance(new Double(0), "none"));
	}

	/**
	 * Update Distance Utility for updating the distance once a smaller distance is
	 * found
	 * 
	 * @param distanceMap
	 * @param mindistanceSrcNode
	 * @param destinationRoute
	 * @param includeTraffic
	 */
	private static void updateDistance(
			HashMap<String, Distance> distanceMap,
			String mindistanceSrcNode, Route destinationRoute,
			Boolean includeTraffic) {

		Distance shortestpathtillnow = distanceMap
				.get(destinationRoute.getPlanetDestination());

		Double newdistance = distanceMap.get(mindistanceSrcNode)
				.getDistance()
				+ destinationRoute.getWeight(includeTraffic);

		if (Double.compare(newdistance,
				shortestpathtillnow.getDistance()) < 0) {
			distanceMap.put(destinationRoute.getPlanetDestination(),
					new Distance(newdistance, mindistanceSrcNode));
		}

	}

	/**
	 * Check validation constrains
	 * 
	 * @param destination
	 * @param mindistanceSrcNode
	 * @param distanceMap
	 * @param includedInPath
	 * @return
	 */
	private static boolean checkvalidcondition(Route destination,
			String mindistanceSrcNode,
			HashMap<String, Distance> distanceMap,
			HashMap<String, Boolean> includedInPath) {

		if (!includedInPath.get(destination.getPlanetDestination())
				&& !distanceMap.get(mindistanceSrcNode).getDistance()
						.equals(Double.MAX_VALUE))
			return true;

		return false;
	}

	/**
	 * Find minimum distance from hashMap
	 * based on value
	 * @param distanceMap
	 * @return
	 */
	public static String findMinDistanceNode(
			HashMap<String, Distance> distanceMap,
			HashMap<String, Boolean> includedInPath) {

		Entry<String, Distance> min = null;
		for (Entry<String, Distance> entry : distanceMap.entrySet()) {

			if (includedInPath.get(entry.getKey()))
				continue;

			if (min == null || (min.getValue().getDistance() > entry
					.getValue().getDistance())) {
				min = entry;
			}
		}

		return min.getKey();

	}

}
