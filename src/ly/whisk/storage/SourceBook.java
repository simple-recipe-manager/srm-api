package ly.whisk.storage;

import java.util.Set;
import java.util.UUID;

import ly.whisk.db.RemoteTable;
import ly.whisk.db.RemotedTable;
import ly.whisk.db.RemotedTableMarshaller;
import ly.whisk.db.RemotedTableSetMarhsaller;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = SourceBook.TABLE_NAME)
public class SourceBook implements RemotedTable {

	public static final String TABLE_NAME = "SourceBooks";

	private UUID id;
	private Set<Author> authors;
	private String title;
	private String ISBN;
	private Note notes;

	@DynamoDBHashKey
	public String getId() {
		return id.toString();
	}

	public void setId(String id) {
		this.id = UUID.fromString(id);
	}

	@RemoteTable(inflationClass = Author.class)
	@DynamoDBMarshalling(marshallerClass = RemotedTableSetMarhsaller.class)
	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	@RemoteTable
	@DynamoDBMarshalling(marshallerClass = RemotedTableMarshaller.class)
	public Note getNotes() {
		return notes;
	}

	public void setNotes(Note notes) {
		this.notes = notes;
	}
}
