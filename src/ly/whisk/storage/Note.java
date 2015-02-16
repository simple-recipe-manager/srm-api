package ly.whisk.storage;

import java.util.UUID;

import ly.whisk.db.RemotedTable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = Note.TABLE_NAME)
public class Note implements RemotedTable {
	public static final String TABLE_NAME = "Notes";

	private UUID id;
	private String note;

	@DynamoDBHashKey
	public String getId() {
		return id.toString();
	}

	public void setId(String id) {
		this.id = UUID.fromString(id);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
