package ly.whisk.storage;

import java.util.UUID;

import ly.whisk.db.RemotedTable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

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

	@DynamoDBHashKey
	public String getId() {
		return id.toString();
	}

	public void setId(String id) {
		this.id = UUID.fromString(id);
	}
}
