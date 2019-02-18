package Model;

import java.util.Date;

public class Lottery {

	// -------------------------------Class  Members------------------------------
	
	private int lotteryNumber;
	private Date dateLot;
	private int maxParticipants;
	private int numberOfWinners;
	private int numberOfBonuses;
	private Boolean lotStatus;
	
	// -------------------------------Constructor------------------------------
	
	public Lottery(int lotteryNumber, Date dateLot, int maxParticipants, int numberOfWinners, int numberOfBonuses, Boolean lotStatus) {
		this.lotteryNumber = lotteryNumber;
		this.dateLot = dateLot;
		this.maxParticipants = maxParticipants;
		this.numberOfWinners = numberOfWinners;
		this.numberOfBonuses = numberOfBonuses;
		this.lotStatus = lotStatus;
	}
	
	
	// -------------------------------Getters And Setters------------------------------
	
	public int getLotteryNumber() {
		return lotteryNumber;
	}

	public Boolean getLotStatus() {
		return lotStatus;
	}


	public void setLotStatus(Boolean lotStatus) {
		this.lotStatus = lotStatus;
	}


	public void setLotteryNumber(int lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public Date getDateLot() {
		return dateLot;
	}

	public void setDateLot(Date dateLot) {
		this.dateLot = dateLot;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public int getNumberOfWinners() {
		return numberOfWinners;
	}

	public void setNumberOfWinners(int numberOfWinners) {
		this.numberOfWinners = numberOfWinners;
	}

	public int getNumberOfBonuses() {
		return numberOfBonuses;
	}

	public void setNumberOfBonuses(int numberOfBonuses) {
		this.numberOfBonuses = numberOfBonuses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lotteryNumber;
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
		Lottery other = (Lottery) obj;
		if (lotteryNumber != other.lotteryNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lottery Number: " + lotteryNumber + ", Date: " + dateLot + ", Max Participants: "
				+ maxParticipants + ", Number Of Winners: " + numberOfWinners + ", Number of Bonuses: " + numberOfBonuses
				+ "";
	}
	
	
}
