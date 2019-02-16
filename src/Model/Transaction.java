package Model;

import java.util.Date;

public class Transaction {

	// -------------------------------Class  Members------------------------------
	
	private int transactionID;
	private int size;
	private String type; //[confirm, pay]
	private double fee;
	private String blockAddress;
	private Date additionTime;
	private Date additionDate;
	
	
	// -------------------------------Constructor------------------------------

	public Transaction(int transactionID, int size, String type, double fee, String blockAddress, Date additionTime,
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
	
	public Transaction(int transactionID, int size, String type, double fee, String blockAddress) {
		super();
		this.transactionID = transactionID;
		this.size = size;
		this.type = type;
		this.fee = fee;
		this.blockAddress = blockAddress;
		this.additionTime = new Date();
		this.additionDate =new Date();
	}

	// -------------------------------Getters And Setters------------------------------
	
	public int getTransactionID() {
		return transactionID;
	}


	public void setTransactionID(int transactionID) {
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
	public String toString() {
		return "Transaction ID: " + transactionID + ", size: " + size + ", type: " + type + ", fee: " + fee
				+ ", block Address: " + blockAddress + ", addition Time: " + additionTime + ", additionDate: " + additionDate
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + transactionID;
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
		if (transactionID != other.transactionID)
			return false;
		return true;
	}

	
	
	
}
