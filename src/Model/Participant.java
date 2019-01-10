package Model;

public class Participant {

	private int lotteryNumber;
	private String uniqueAddress;
	private String isWinner;
	
	public Participant(int lotteryNumber, String uniqueAddress, String isWinner) {
		this.lotteryNumber = lotteryNumber;
		this.uniqueAddress = uniqueAddress;
		this.isWinner = isWinner;
	}

	public int getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(int lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public String getUniqueAddress() {
		return uniqueAddress;
	}

	public void setUniqueAddress(String uniqueAddress) {
		this.uniqueAddress = uniqueAddress;
	}

	public String getIsWinner() {
		return isWinner;
	}

	public void setIsWinner(String isWinner) {
		this.isWinner = isWinner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lotteryNumber;
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
		Participant other = (Participant) obj;
		if (lotteryNumber != other.lotteryNumber)
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
		return "Participant [lotteryNumber=" + lotteryNumber + ", uniqueAddress=" + uniqueAddress + ", isWinner="
				+ isWinner + "]";
	}
	
	
}
