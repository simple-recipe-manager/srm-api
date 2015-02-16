package ly.whisk.storage;

import java.util.UUID;

import ly.whisk.db.RemoteTable;
import ly.whisk.db.RemotedTable;
import ly.whisk.db.RemotedTableMarshaller;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = Yield.TABLE_NAME)
public class Yield implements RemotedTable {

	public static final String TABLE_NAME = "Yields";

	private UUID id;
	private long serves;
	private UnitTag unit;

	public Yield() {

	}

	public long getServes() {
		return serves;
	}

	public void setServes(long serves) {
		this.serves = serves;
	}

	@DynamoDBMarshalling(marshallerClass = RemotedTableMarshaller.class)
	@RemoteTable
	public UnitTag getUnit() {
		return unit;
	}

	public void setUnit(UnitTag unit) {
		this.unit = unit;
	}

	@DynamoDBHashKey
	public String getId() {
		return id.toString();
	}

	@Override
	public void setId(String id) {
		this.id = UUID.fromString(id);
	}

	@Override
	public String toString() {
		return this.id.toString();
	}
}
