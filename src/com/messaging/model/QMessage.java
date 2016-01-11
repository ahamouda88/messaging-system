package com.messaging.model;

import java.util.Date;

public class QMessage {

	private final long id;
	private final String message;
	private final Date datePublished;
	
	public QMessage(long id, String message, Date datePublished) {
		this.id = id;
		this.message = message;
		this.datePublished = datePublished;
	}
	
	public long getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Date getDatePublished() {
		return datePublished;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((datePublished == null) ? 0 : datePublished.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QMessage other = (QMessage) obj;
		if (datePublished == null) {
			if (other.datePublished != null)
				return false;
		} else if (!datePublished.equals(other.datePublished))
			return false;
		if (id != other.id)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Header: ID= ");
		sb.append(id);
		sb.append(", Date Published= ");
		sb.append(datePublished);
		sb.append("\n");
		sb.append("Message: ");
		sb.append(message);
		return sb.toString();
	}
}
