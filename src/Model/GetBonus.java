package Model;

public class GetBonus {

	// -------------------------------Class  Members------------------------------
	
	private int lotteryNumber;
	private String uniqueAddress;
	private int bonusNumber;
	
	// -------------------------------Constructor------------------------------
	
	public GetBonus(int lotteryNumber, String uniqueAddress, int bonusNumber) {
		super();
		this.lotteryNumber = lotteryNumber;
		this.uniqueAddress = uniqueAddress;
		this.bonusNumber = bonusNumber;
	}
	
	
	// -------------------------------Getters And Setters------------------------------
	
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
	public int getBonusNumber() {
		return bonusNumber;
	}
	public void setBonusNumber(int bonusNumber) {
		this.bonusNumber = bonusNumber;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bonusNumber;
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
		GetBonus other = (GetBonus) obj;
		if (bonusNumber != other.bonusNumber)
			return false;
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
		return "GetBonus [lotteryNumber=" + lotteryNumber + ", uniqueAddress=" + uniqueAddress + ", bonusNumber="
				+ bonusNumber + "]";
	}
	
	
	
}
