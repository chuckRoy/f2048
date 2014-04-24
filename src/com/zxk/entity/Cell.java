package com.zxk.entity;

import java.util.List;

public class Cell {

	List<List<Grid>> cellLists;
	
	public Cell() {}

	public Cell(List<List<Grid>> cellLists) {
		super();
		this.cellLists = cellLists;
	}

	public List<List<Grid>> getCellLists() {
		return cellLists;
	}

	public void setCellLists(List<List<Grid>> cellLists) {
		this.cellLists = cellLists;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Grid g = cellLists.get(i).get(j);
				sb.append(i + "," + j + ":" + g + "\n");
			}
		}
//		return "Cell [cellLists=" + cellLists + "]";
		return sb.toString();
	}
}
