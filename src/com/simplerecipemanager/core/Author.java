package com.simplerecipemanager.core;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.simplerecipemanager.db.RemotedTable;
import com.simplerecipemanager.db.UUIDMarshaller;

@DynamoDBTable(tableName = Author.TABLE_NAME)
public class Author implements RemotedTable {
	public static final String TABLE_NAME = "Authors";
	private UUID id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
	@DynamoDBHashKey
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
