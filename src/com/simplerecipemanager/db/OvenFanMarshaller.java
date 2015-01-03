package com.simplerecipemanager.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.simplerecipemanager.core.OvenFan;

public class OvenFanMarshaller implements DynamoDBMarshaller<OvenFan> {

	@Override
	public String marshall(OvenFan arg0) {
		return arg0.toString();
	}

	@Override
	public OvenFan unmarshall(Class<OvenFan> arg0, String arg1) {
		return OvenFan.valueOf(arg1);
	}
}
