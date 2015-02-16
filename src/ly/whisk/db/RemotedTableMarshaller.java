package ly.whisk.db;

import java.io.IOException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RemotedTableMarshaller implements DynamoDBMarshaller<RemotedTable> {

	@Override
	public String marshall(RemotedTable getterReturnResult) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		try {
			return mapper.writeValueAsString(getterReturnResult);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public RemotedTable unmarshall(Class<RemotedTable> clazz, String obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		try {
			return mapper.readValue(obj, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
