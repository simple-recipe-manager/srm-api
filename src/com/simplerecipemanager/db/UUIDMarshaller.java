package com.simplerecipemanager.db;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;

public class UUIDMarshaller implements DynamoDBMarshaller<UUID> {

	@Override
	public String marshall(UUID arg0) {
		return arg0.toString();
	}

	@Override
	public UUID unmarshall(Class<UUID> arg0, String arg1) {
		return UUID.fromString(arg1);
	}

}
