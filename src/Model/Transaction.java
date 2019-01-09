package Model;

import java.util.Date;

public abstract class Transaction {

	// -------------------------------Class  Members------------------------------
	
	private String transactionID;
	private int size;
	private String type; //[confirm, pay]
	private double fee;
	private String blockAddress;
	private Date additionTime;
	private Date additionDate;
	
	
	// -------------------------------Constructor------------------------------

	public Transaction(String transactionID, int size, String type, double fee, String blockAddress, Date additionTime,
			Date additionDate) {
		super();
		this.transactionID = transactionID;
		this.size = size;
		this.type = type;
		this.fee = fee;
		this.blockAddress = blockAddress;
		this.additionTime = additionTime;
		this.additionDate = additionDate;
	}

	// -------------------------------Getters And Setters------------------------------
	
	public String getTransactionID() {
		return transactionID;
	}


	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public double getFee() {
		return fee;
	}


	public void setFee(double fee) {
		this.fee = fee;
	}


	public String getBlockAddress() {
		return blockAddress;
	}


	public void setBlockAddress(String blockAddress) {
		this.blockAddress = blockAddress;
	}


	public Date getAdditionTime() {
		return additionTime;
	}


	public void setAdditionTime(Date additionTime) {
		this.additionTime = additionTime;
	}


	public Date getAdditionDate() {
		return additionDate;
	}


	public void setAdditionDate(Date additionDate) {
		this.additionDate = additionDate;
	}
	// -------------------------------Other Methods------------------------------
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((transactionID == null) ? 0 : transactionID.hashCode());
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
		Transaction other = (Transaction) obj;
		if (transactionID == null) {
			if (other.transactionID != null)
				return false;
		} else if (!transactionID.equals(other.transactionID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", size=" + size + ", type=" + type + ", fee=" + fee
				+ ", blockAddress=" + blockAddress + ", additionTime=" + additionTime + ", additionDate=" + additionDate
				+ "]";
	}

	
	
	
}
