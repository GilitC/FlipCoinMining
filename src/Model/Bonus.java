package Model;

public class Bonus {

	private int bonusNumber;
	private String Description;
	
	public Bonus(int bonusNumber, String description) {
		super();
		this.bonusNumber = bonusNumber;
		Description = description;
	}

	public int getBonusNumber() {
		return bonusNumber;
	}

	public void setBonusNumber(int bonusNumber) {
		this.bonusNumber = bonusNumber;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Description == null) ? 0 : Description.hashCode());
		result = prime * result + bonusNumber;
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
		Bonus other = (Bonus) obj;
		if (Description == null) {
			if (other.Description != null)
				return false;
		} else if (!Description.equals(other.Description))
			return false;
		if (bonusNumber != other.bonusNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bonus [bonusNumber=" + bonusNumber + ", Description=" + Description + "]";
	}
	
	
}
