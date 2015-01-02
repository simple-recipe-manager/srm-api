package com.simplerecipemanager.core;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.simplerecipemanager.db.UUIDMarshaller;

@DynamoDBTable(tableName = Note.TABLE_NAME)
public class Note {
	public static final String TABLE_NAME = "Notes";

	private UUID id;
	private String note;

	@DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
	@DynamoDBHashKey
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
