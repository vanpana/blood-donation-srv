package com.cyberschnitzel.Domain.Entities;
import java.util.Date;

public class Thrombocites extends BloodPart {

	public static Integer valabilityDays = 10; //Todo: change values here
	private Blood origin;
	private Integer idOrigin;
	private Integer quantity;
	public Thrombocites(String bloodType, Blood o) {
		super(bloodType);
		origin = o;
		idOrigin = o.getId();
	}
	public Thrombocites(Integer _id, Integer _idblood, Date _exp)
	{
		super(_id, _idblood, _exp);
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
	public static Thrombocites fromBloodPart(BloodPart bp){
		return new Thrombocites(bp.getId(), bp.getIdBlood(), bp.getExp());
	}

	@Override
	public String getType()
	{
		return "Thrombocites";
	}


}
