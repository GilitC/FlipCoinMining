package Model;

import java.util.Date;

public class Block {

	// -------------------------------Class  Members------------------------------
	
	public int blockAddress;
	public Date creationDate;
	public Date creationHour;
	public int size;
	public int previousBlock;
	public String minerAddress;
	
	// -------------------------------Constructor------------------------------
	
	public Block(int blockAddress, Date creationDate, Date creationHour, int size, int previousBlock,
			String minerAddress) {
		this.blockAddress = blockAddress;
		this.creationDate = creationDate;
		this.creationHour = creationHour;
		this.size = size;
		this.previousBlock = previousBlock;
		this.minerAddress = minerAddress;
	}

	// -------------------------------Getters And Setters------------------------------
	
	public int getBlockAddress() {
		return blockAddress;
	}


	public void setBlockAddress(int blockAddress) {
		this.blockAddress = blockAddress;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Date getCreationHour() {
		return creationHour;
	}


	public void setCreationHour(Date creationHour) {
		this.creationHour = creationHour;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public int getPreviousBlock() {
		return previousBlock;
	}


	public void setPreviousBlock(int previousBlock) {
		this.previousBlock = previousBlock;
	}


	public String getMinerAddress() {
		return minerAddress;
	}


	public void setMinerAddress(String minerAddress) {
		this.minerAddress = minerAddress;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blockAddress;
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
		Block other = (Block) obj;
		if (blockAddress != other.blockAddress)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Block Address:" + blockAddress + ", creation Date: " + creationDate + ", creation Hour: "
				+ creationHour + ", size=" + size + ", previous Block: " + previousBlock + ", minerAddress: "
				+ minerAddress + "";
	}
	
	
}
