package com.simplerecipemanager.db;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RemotedTableSetMarhsaller implements
		DynamoDBMarshaller<Set<? extends RemotedTable>> {

	@Override
	public String marshall(Set<? extends RemotedTable> getterReturnResult) {
		Set<String> idSet = new HashSet<String>();
		for (RemotedTable item : getterReturnResult) {
			idSet.add(item.getId().toString());
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		try {
			return mapper.writeValueAsString(idSet);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<? extends RemotedTable> unmarshall(
			Class<Set<? extends RemotedTable>> clazz, String obj) {
		ObjectMapper mapper = new ObjectMapper();
		Set<String> idSet;
		try {
			idSet = mapper.readValue(obj, new TypeReference<Set<String>>() {
			});
			Set<RemotedTable> toReturn = new HashSet<>();
			for (final String s : idSet) {
				RemotedTable rt = new RemotedTable() {

					@Override
					public UUID getId() {

						return UUID.fromString(s);
					}

					@Override
					public void setId(UUID id) {
						// TODO Auto-generated method stub

					}
				};
				toReturn.add(rt);
			}
			return toReturn;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
