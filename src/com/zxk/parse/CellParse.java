package com.zxk.parse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import com.zxk.entity.Cell;
import com.zxk.entity.Grid;

public class CellParse {

	public static Cell getCell(String json) throws Exception {

		Cell cell = new Cell();
		List<List<Grid>> gridLists = new ArrayList<List<Grid>>();

		ObjectMapper objectMapper = new ObjectMapper();
		List<List<LinkedHashMap<String, Object>>> list = objectMapper .readValue(json, List.class);
		for (int i = 0; i < 4; i++) {
			List<Grid> grids = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				Grid g = new Grid();
				Map<String, Object> map = list.get(i).get(j);
				if (map == null) {
					grids.add(g);
					continue;
				}
				Set<String> set = map.keySet();
				for (Iterator<String> it = set.iterator(); it.hasNext();) {
					String key = it.next();
					switch (key) {
					case "x":
						g.setX((int) map.get(key));
						break;
					case "y":
						g.setY((int) map.get(key));
						break;
					case "value":
						g.setValue((int) map.get(key));
						break;
//					case "previousPosition":
//						g.setPreviousPosition((String) map.get(key));
//						break;
//					case "mergedFrom":
//						g.setMergedFrom((String) map.get(key));
//						break;
					default:
						break;
					}
				}
				grids.add(g);
			}
			gridLists.add(grids);
		}
		cell.setCellLists(gridLists);
		return cell;
	}
	
	
	public static int[][] cellToArray(Cell cell) {
		
		int[][] matrix = new int[][] {
	        new int[] { 0, 0, 0, 0},
	        new int[] { 0, 0, 0, 0},
	        new int[] { 0, 0, 0, 0},
	        new int[] { 0, 0, 0, 0}
		};
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Grid g = cell.getCellLists().get(i).get(j);
				int value = g.getValue();
				if(value!=0) {
					int x = g.getX();
					int y = g.getY();
					matrix[y][x] = value;
				}
			}
		}
		
		/*for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(matrix[i][j] + ",");
			}
			System.out.println();
		}*/
		return matrix;
	}
	
	public static void main(String[] args) throws Exception {
		String json = "[[null,null,{\"x\":0,\"y\":2,\"value\":2,\"previousPosition\":null,\"mergedFrom\":null},null],[null,null,null,null],[null,null,null,null],[null,null,null,{\"x\":3,\"y\":3,\"value\":2,\"previousPosition\":null,\"mergedFrom\":null}]]";
		Cell cell = CellParse.getCell(json);
		System.out.println(cell);
		cellToArray(cell);
	}

}
