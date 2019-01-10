package Model;

import java.sql.Time;

public class SolvedRiddle {

	private String uniqueAddress;
	private int riddleNumber;
	private Time time;
	
	public SolvedRiddle(String uniqueAddress, int riddleNumber, Time time) {
		this.uniqueAddress = uniqueAddress;
		this.riddleNumber = riddleNumber;
		this.time = time;
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

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
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
		return "SolvedRiddle [uniqueAddress=" + uniqueAddress + ", riddleNumber=" + riddleNumber + ", time=" + time
				+ "]";
	}
	
	
}
