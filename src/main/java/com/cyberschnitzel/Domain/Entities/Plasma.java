package com.cyberschnitzel.Domain.Entities;
import java.util.Date;

public class Plasma extends BloodPart {

	public static Integer valabilityDays = 10; //Todo: change values here
	private Blood origin;
	private Integer idOrigin;
	private Integer quantity;
	public Plasma(String bloodType, Blood o) {
		super(bloodType);
		origin = o;
		idOrigin = o.getId();
	}
	public Date getExpirationDate()
	{
		Date received = super.getReceivedDate();
		return null;
	}

	public Blood getOrigin()
	{
		return origin;

	}
	@Override
	public String getType()
	{
		return "Plasma";
	}


}
