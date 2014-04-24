package com.zxk.entity;

public class Grid {
	int x;
	int y;
	int value;

	
	public Grid() {}

	public Grid(int x, int y, int value) {
		super();
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Grid [x=" + x + ", y=" + y + ", value=" + value + "]";
	}
	
}

//public class Grid {
//	int x;
//	int y;
//	int value;
//	String previousPosition;
//	String mergedFrom;
//
//	
//	public Grid() {}
//
//	public Grid(int x, int y, int value, String previousPosition,
//			String mergedFrom) {
//		super();
//		this.x = x;
//		this.y = y;
//		this.value = value;
//		this.previousPosition = previousPosition;
//		this.mergedFrom = mergedFrom;
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public int getValue() {
//		return value;
//	}
//
//	public void setValue(int value) {
//		this.value = value;
//	}
//
//	public String getPreviousPosition() {
//		return previousPosition;
//	}
//
//	public void setPreviousPosition(String previousPosition) {
//		this.previousPosition = previousPosition;
//	}
//
//	public String getMergedFrom() {
//		return mergedFrom;
//	}
//
//	public void setMergedFrom(String mergedFrom) {
//		this.mergedFrom = mergedFrom;
//	}
//
//	@Override
//	public String toString() {
//		return "Grid [x=" + x + ", y=" + y + ", value=" + value
//				+ ", previousPosition=" + previousPosition
//				+ ", mergedFrom=" + mergedFrom + "]";
//	}
//}