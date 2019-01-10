package Model;

import java.sql.Time;
import java.util.Date;

public class Block {

	// -------------------------------Class  Members------------------------------
	
	public String blockAddress;
	public Date creationDate;
	public Time creationHour;
	public int size;
	public String previousBlock;
	public String minerAddress;
	
	// -------------------------------Constructor------------------------------
	
	public Block(String blockAddress, Date creationDate, Time creationHour, int size, String previousBlock,
			String minerAddress) {
		this.blockAddress = blockAddress;
		this.creationDate = creationDate;
		this.creationHour = creationHour;
		this.size = size;
		this.previousBlock = previousBlock;
		this.minerAddress = minerAddress;
	}

	// -------------------------------Getters And Setters------------------------------
	
	public String getBlockAddress() {
		return blockAddress;
	}


	public void setBlockAddress(String blockAddress) {
		this.blockAddress = blockAddress;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Time getCreationHour() {
		return creationHour;
	}


	public void setCreationHour(Time creationHour) {
		this.creationHour = creationHour;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public String getPreviousBlock() {
		return previousBlock;
	}


	public void setPreviousBlock(String previousBlock) {
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
		result = prime * result + ((blockAddress == null) ? 0 : blockAddress.hashCode());
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
		if (blockAddress == null) {
			if (other.blockAddress != null)
				return false;
		} else if (!blockAddress.equals(other.blockAddress))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Block: Address=" + blockAddress + ", creationDate=" + creationDate + ", creationHour="
				+ creationHour + ", size=" + size + ", previousBlock=" + previousBlock + ", minerAddress="
				+ minerAddress + "|";
	}
	
	
}
