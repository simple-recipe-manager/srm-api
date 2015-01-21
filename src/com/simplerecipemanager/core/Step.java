package com.simplerecipemanager.core;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.simplerecipemanager.db.HACCPMarshaller;
import com.simplerecipemanager.db.RemoteTable;
import com.simplerecipemanager.db.RemotedTable;
import com.simplerecipemanager.db.RemotedTableMarshaller;

@DynamoDBTable(tableName = Step.TABLE_NAME)
public class Step implements RemotedTable {

	public static final String TABLE_NAME = "Steps";

	private UUID id;
	private String stepDetails;
	private HACCP haccp;
	private Note notes;
	private int order;

	@DynamoDBHashKey
	public String getId() {
		return id.toString();
	}

	public void setId(String id) {
		this.id = UUID.fromString(id);
	}

	public String getStepDetails() {
		return stepDetails;
	}

	public void setStepDetails(String stepDetails) {
		this.stepDetails = stepDetails;
	}

	@DynamoDBMarshalling(marshallerClass = HACCPMarshaller.class)
	public HACCP getHaccp() {
		return haccp;
	}

	public void setHaccp(HACCP haccp) {
		this.haccp = haccp;
	}

	@RemoteTable
	@DynamoDBMarshalling(marshallerClass = RemotedTableMarshaller.class)
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
