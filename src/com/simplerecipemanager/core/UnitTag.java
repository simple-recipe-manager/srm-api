package com.simplerecipemanager.core;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.simplerecipemanager.db.RemotedTable;
import com.simplerecipemanager.db.UUIDMarshaller;

@DynamoDBTable(tableName = UnitTag.TABLE_NAME)
public class UnitTag implements RemotedTable {
	public static final String TABLE_NAME = "Units";
	private UUID id;
	private String tag;

	@DynamoDBHashKey
	@DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}