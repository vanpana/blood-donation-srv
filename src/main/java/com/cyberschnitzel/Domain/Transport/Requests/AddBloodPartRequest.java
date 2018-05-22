package com.cyberschnitzel.Domain.Transport.Requests;

import java.util.Date;

public class AddBloodPartRequest extends MessageRequest{

	private Integer bloodId, partId;
	private Date expDate;
	private Class partClass;
	private Float quantity;

	public Float getQuantity() {
		return quantity;
	}

	public AddBloodPartRequest(String email, String password, String token, Integer bloodId, Integer partId, Date expDate, Class partClass, Float quantity) {
		super(email, password, token);
		this.bloodId = bloodId;
		this.partId = partId;
		this.expDate = expDate;
		this.partClass = partClass;
		this.quantity = quantity;
	}

	public Integer getBloodId() {
		return bloodId;
	}

	public Integer getPartId() {
		return partId;
	}

	public Date getExpDate() {
		return expDate;
	}

	public Class getPartClass() {
		return partClass;
	}

	public AddBloodPartRequest(String email, String password, String token, Integer _partId, Integer _bloodId, Date _exp, Class _partClass) {
		super(email, password, token);
		bloodId = _bloodId;
		partId = _partId;
		expDate = _exp;
		partClass = _partClass;

	}
}
