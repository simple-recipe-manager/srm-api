package com.amazonaws.services.dynamodbv2.datamodeling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.simplerecipemanager.db.RemoteTable;
import com.simplerecipemanager.db.RemotedTable;

public class RemoteTableDynamoDBMapper extends DynamoDBMapper {

	private DynamoDBReflector reflector = new DynamoDBReflector();

	public RemoteTableDynamoDBMapper(AmazonDynamoDB ddb,
			AWSCredentialsProvider s3CredentialProvider) {
		super(ddb, s3CredentialProvider);
	}

	public RemoteTableDynamoDBMapper(AmazonDynamoDB ddb) {
		super(ddb);
	}

	@Override
	public <T> void save(T object) {
		Set<? extends RemotedTable> testSet = new HashSet<>();
		Collection<Method> methods = reflector.getRelevantGetters(object
				.getClass());
		for (Method m : methods) {
			try {
				if (m.getAnnotation(RemoteTable.class) != null) {
					// This field should be saved to it's own table, and the
					// value
					// replaced with the 'hash key' of the saved
					Object remoteObjectToStore = m.invoke(object);
					if (remoteObjectToStore == null) {
						continue;
					}
					if (remoteObjectToStore instanceof RemotedTable) {
						RemotedTable castedToStore = (RemotedTable) remoteObjectToStore;
						RemotedTable toReplaceWith = (RemotedTable) remoteObjectToStore
								.getClass().newInstance();
						toReplaceWith.setId(castedToStore.getId());
						RemoteTableDynamoDBMapper.this
								.save(remoteObjectToStore);
						Method setter = reflector.getSetter(m);
						setter.invoke(object, toReplaceWith);

					} else if (remoteObjectToStore instanceof Set
							&& remoteObjectToStore.getClass().isAssignableFrom(
									testSet.getClass())) {

						Set<T> toStoreAsReplacement = new HashSet<>();

						for (RemotedTable item : (Set<? extends RemotedTable>) remoteObjectToStore) {
							RemotedTable toReplaceWith = (RemotedTable) item
									.getClass().newInstance();
							toReplaceWith.setId(item.getId());
							RemoteTableDynamoDBMapper.this.save(item);
							toStoreAsReplacement.add((T) toReplaceWith);
						}

						Method setter = reflector.getSetter(m);
						setter.invoke(object, toStoreAsReplacement);

					} else {
						throw new RuntimeException(
								"Must be an instance of RemotedTable");
					}

				}
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | InstantiationException ex) {
				throw new RuntimeException(ex);
			}
		}
		super.save(object);
	}

	@Override
	public <T extends Object> T load(Class<T> clazz, Object hashKey,
			Object rangeKey, DynamoDBMapperConfig config) {
		T toFillIn = super.load(clazz, hashKey, rangeKey, config);
		if (toFillIn == null) {
			return null;
		}
		Collection<Method> methods = reflector.getRelevantGetters(clazz);
		for (Method m : methods) {
			try {
				if (m.getAnnotation(RemoteTable.class) != null) {
					// This should just have a holder object, with an ID
					Object idHolder = m.invoke(toFillIn);
					if (idHolder == null) {
						continue;
					}
					if (idHolder instanceof Set) {
						Set<Object> toPutBack = new HashSet<Object>();
						Set<? extends RemotedTable> keysToLoad = (Set<? extends RemotedTable>) idHolder;
						for (RemotedTable toLoad : keysToLoad) {
							RemotedTable loadMe = m
									.getAnnotation(RemoteTable.class)
									.inflationClass().newInstance();
							loadMe.setId(toLoad.getId());
							Object loaded = RemoteTableDynamoDBMapper.this
									.load(loadMe);

							toPutBack.add(loaded);
						}
						Method setter = reflector.getSetter(m);
						setter.invoke(toFillIn, toPutBack);
					} else {
						Object loaded = RemoteTableDynamoDBMapper.this
								.load(idHolder);
						if (loaded == null) {
							throw new RuntimeException(
									"Could not load remote object");
						}
						Method setter = reflector.getSetter(m);
						setter.invoke(toFillIn, loaded);
					}
				}
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | InstantiationException ex) {
				throw new RuntimeException(ex);
			}
		}
		return toFillIn;

	}
}
