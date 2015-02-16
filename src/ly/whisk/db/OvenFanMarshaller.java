package ly.whisk.db;

import ly.whisk.storage.OvenFan;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;

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
