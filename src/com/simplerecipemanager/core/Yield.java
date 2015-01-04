package com.simplerecipemanager.core;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.simplerecipemanager.db.RemoteTable;
import com.simplerecipemanager.db.RemotedTableMarshaller;

public class Yield {
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
}
