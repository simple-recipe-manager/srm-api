package com.simplerecipemanager.core;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.simplerecipemanager.db.UUIDMarshaller;

public class Step {

	private UUID id;
	private String stepDetails;
	private HACCP haccp;
	private Note notes;
	private int order;

	@DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getStepDetails() {
		return stepDetails;
	}

	public void setStepDetails(String stepDetails) {
		this.stepDetails = stepDetails;
	}

	public HACCP getHaccp() {
		return haccp;
	}

	public void setHaccp(HACCP haccp) {
		this.haccp = haccp;
	}

	public Note getNotes() {
		return notes;
	}

	public void setNotes(Note notes) {
		this.notes = notes;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
