package Model;

import java.sql.Date;

public class Riddle {

	private int riddleNumber;
	private Date publishTime;
	private Date publishDate;
	private String description;
	private Date solutionTime;
	private String status; // can be: Solved or Unsolved
	private int riddleLevel;
	
	public Riddle(int riddleNumber, Date publishTime, Date publishDate, String description, Date solutionTime,
			String status, int riddleLevel) {
		super();
		this.riddleNumber = riddleNumber;
		this.publishTime = publishTime;
		this.publishDate = publishDate;
		this.description = description;
		this.solutionTime = solutionTime;
		this.status = status;
		this.riddleLevel = riddleLevel;
	}

	public int getRiddleNumber() {
		return riddleNumber;
	}

	public void setRiddleNumber(int riddleNumber) {
		this.riddleNumber = riddleNumber;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getSolutionTime() {
		return solutionTime;
	}

	public void setSolutionTime(Date solutionTime) {
		this.solutionTime = solutionTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRiddleLevel() {
		return riddleLevel;
	}

	public void setRiddleLevel(int riddleLevel) {
		this.riddleLevel = riddleLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + riddleNumber;
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
		Riddle other = (Riddle) obj;
		if (riddleNumber != other.riddleNumber)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Riddle Number " + riddleNumber + ", published at " + publishTime + ", On "
				+ publishDate + ", DeadLine: " + solutionTime + " Description: " + description+ " Level: " + riddleLevel + " Status: " +status;
	}
	
	
	
}
