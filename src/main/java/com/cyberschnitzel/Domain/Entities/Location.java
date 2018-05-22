package com.cyberschnitzel.Domain.Entities;


public class Location extends Entity {
	private String name;
	private double x, y;
	private Integer idLocation;

	public Location()
	{

	}

	public Location(String name, double x, double y, Integer idLocation) {
		this.setId(idLocation);
		this.name = name;
		this.x = x;
		this.y = y;
		this.idLocation = idLocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Integer getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(Integer idLocation) {
		this.idLocation = idLocation;
	}
}
