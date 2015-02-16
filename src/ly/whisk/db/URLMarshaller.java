package ly.whisk.db;

import java.net.MalformedURLException;
import java.net.URL;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;

public class URLMarshaller implements DynamoDBMarshaller<URL> {

	@Override
	public String marshall(URL getterReturnResult) {
		return getterReturnResult.toString();
	}

	@Override
	public URL unmarshall(Class<URL> clazz, String obj) {
		try {
			return new URL(obj);
		} catch (MalformedURLException e) {
			return null;
		}
	}

}
