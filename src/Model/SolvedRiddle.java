package Model;

import java.sql.Time;
import java.util.Date;

public class SolvedRiddle {

	private String uniqueAddress;
	private int riddleNumber;
	private Date time;
	private int place;
	
	public SolvedRiddle(String uniqueAddress, int riddleNumber, Date time, int place) {
		this.uniqueAddress = uniqueAddress;
		this.riddleNumber = riddleNumber;
		this.time = time;
		this.place = place;
	}

	public String getUniqueAddress() {
		return uniqueAddress;
	}

	public void setUniqueAddress(String uniqueAddress) {
		this.uniqueAddress = uniqueAddress;
	}

	public int getRiddleNumber() {
		return riddleNumber;
	}

	public void setRiddleNumber(int riddleNumber) {
		this.riddleNumber = riddleNumber;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	
	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + riddleNumber;
		result = prime * result + ((uniqueAddress == null) ? 0 : uniqueAddress.hashCode());
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
		SolvedRiddle other = (SolvedRiddle) obj;
		if (riddleNumber != other.riddleNumber)
			return false;
		if (uniqueAddress == null) {
			if (other.uniqueAddress != null)
				return false;
		} else if (!uniqueAddress.equals(other.uniqueAddress))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Solved Riddle By: " + uniqueAddress + ", Riddle Number: " + riddleNumber + ", time and date: " + time
				+ "place: " +this.place;
	}
	
	
}
