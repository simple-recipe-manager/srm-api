package ly.whisk.storage;

import java.util.UUID;

import ly.whisk.db.RemotedTable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = UnitTag.TABLE_NAME)
public class UnitTag implements RemotedTable {
	public static final String TABLE_NAME = "Units";
	private UUID id;
	private String tag;

	@DynamoDBHashKey
	public String getId() {
		return id.toString();
	}

	public void setId(String id) {
		this.id = UUID.fromString(id);
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}