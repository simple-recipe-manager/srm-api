package com.simplerecipemanager.core;

import java.util.Set;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.simplerecipemanager.db.RemoteTable;
import com.simplerecipemanager.db.RemotedTable;
import com.simplerecipemanager.db.RemotedTableMarshaller;
import com.simplerecipemanager.db.RemotedTableSetMarhsaller;
import com.simplerecipemanager.db.UUIDMarshaller;

@DynamoDBTable(tableName = SourceBook.TABLE_NAME)
public class SourceBook implements RemotedTable {

	public static final String TABLE_NAME = "Sources";

	private UUID id;
	private Set<Author> authors;
	private String title;
	private String ISBN;
	private Note notes;

	@DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
	@DynamoDBHashKey
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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
