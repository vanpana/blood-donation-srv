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
	public Plasma(Integer _id, Integer _idblood, Date _exp)
	{
		super(_id, _idblood, _exp);
	}

	@Override
	public Date getExpirationDate()
	{
		Date received = super.getReceivedDate();
		return received;
	}

	public Blood getOrigin()
	{
		return origin;

	}
	public static Plasma fromBloodPart(BloodPart bp){
		return new Plasma(bp.getId(), bp.getIdBlood(), bp.getExpirationDate());
	}

	@Override
	public String getType()
	{
		return "Plasma";
	}


}
