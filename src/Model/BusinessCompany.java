package Model;

public class BusinessCompany{

	// -------------------------------Class Memebers------------------------------
	private String uniqueAddress;
	private String contactName;
	private int contactPhone;
	private String contactEmail;
	
	// -------------------------------Constructor------------------------------
	
	public BusinessCompany(String uniqueAddress, String contactName, int contactPhone, String contactEmail) {
		this.uniqueAddress = uniqueAddress;
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.contactEmail = contactEmail;
	}

	// -------------------------------Getters and Setters------------------------------
	
	public String getUniqueAddress() {
		return uniqueAddress;
	}

	public void setUniqueAddress(String uniqueAddress) {
		this.uniqueAddress = uniqueAddress;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public int getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(int contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		BusinessCompany other = (BusinessCompany) obj;
		if (uniqueAddress == null) {
			if (other.uniqueAddress != null)
				return false;
		} else if (!uniqueAddress.equals(other.uniqueAddress))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BusinessCompany [uniqueAddress=" + uniqueAddress + ", contactName=" + contactName + ", contactPhone="
				+ contactPhone + ", contactEmail=" + contactEmail + "]";
	}
	
	
}
