package com.simplerecipemanager.core;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.simplerecipemanager.db.RemoteTable;
import com.simplerecipemanager.db.RemotedTable;
import com.simplerecipemanager.db.RemotedTableMarshaller;
import com.simplerecipemanager.db.UUIDMarshaller;

@DynamoDBTable(tableName = Yield.TABLE_NAME)
public class Yield implements RemotedTable {

	public static final String TABLE_NAME = "Yields";

	private UUID id;
	private long serves;
	private UnitTag unit;

	public Yield() {

	}

	public long getServes() {
		return serves;
	}

	public void setServes(long serves) {
		this.serves = serves;
	}

	@DynamoDBMarshalling(marshallerClass = RemotedTableMarshaller.class)
	@RemoteTable
	public UnitTag getUnit() {
		return unit;
	}

	public void setUnit(UnitTag unit) {
		this.unit = unit;
	}

	@Override
	@DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
	@DynamoDBHashKey
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;

	}
}
