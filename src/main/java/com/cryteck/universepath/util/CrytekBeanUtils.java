package com.cryteck.universepath.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.cryteck.universepath.model.Distance;
import com.cryteck.universepath.service.DataLoaderService;

public class CrytekBeanUtils {

	/**
	 * Generic bean util class to create list of beans from sheet and class argument
	 * 
	 * @param sheet
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> ArrayList<T> createBeanList(Sheet sheet,
			Class<T> clazz, boolean skipfirstrow) throws Exception {

		ArrayList<T> beansList = new ArrayList<>();

		for (Row row : sheet) {

			if (row.getCell(0) == null || row.getCell(1) == null)
				continue;
			if (!skipfirstrow) {
				beansList.add(clazz.getDeclaredConstructor(Row.class)
						.newInstance(row));
				skipfirstrow = false;
				continue;
			}
			skipfirstrow = false;
		}

		return beansList;

	}

	/**
	 * Library method to create return pojo of distance list from destination to
	 * source
	 * 
	 * @param distanceSourceMap
	 * @param target
	 * @param dataLoaderService
	 * @return
	 */
	public static List<Distance> createDistanceList(
			HashMap<String, Distance> distanceSourceMap,
			String target, DataLoaderService dataLoaderService) {

		String destination = target;

		List<Distance> distanceList = new ArrayList<>();
		Distance distance;
		while(destination != null && !destination.equals("none"))
		{
			distance = distanceSourceMap.get(destination);
			distance.setSourceAlias(
					dataLoaderService.getNodeNameCachedData()
							.get(distance.getSource()) != null
									? dataLoaderService
											.getNodeNameCachedData()
											.get(distance.getSource())
											.getPlanetName()
									: null);

			distance.setCurrentNodeAlias(
					dataLoaderService.getNodeNameCachedData()
							.get(destination).getPlanetName());
			distance.setCurrentnode(destination);
			distanceList.add(distance);
			destination = distance.getSource();
			System.out.println("des" + destination);
		}

		Collections.reverse(distanceList);

		return distanceList;
	}

}
