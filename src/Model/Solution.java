package Model;

public class Solution {

	private int riddleNumber;
	private int solutionNumber;
	private String result;
	
	public Solution(int riddleNumber, int solutionNumber, String result) {
		this.riddleNumber = riddleNumber;
		this.solutionNumber = solutionNumber;
		this.result = result;
	}

	public int getRiddleNumber() {
		return riddleNumber;
	}

	public void setRiddleNumber(int riddleNumber) {
		this.riddleNumber = riddleNumber;
	}

	public int getSolutionNumber() {
		return solutionNumber;
	}

	public void setSolutionNumber(int solutionNumber) {
		this.solutionNumber = solutionNumber;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + riddleNumber;
		result = prime * result + solutionNumber;
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
		Solution other = (Solution) obj;
		if (riddleNumber != other.riddleNumber)
			return false;
		if (solutionNumber != other.solutionNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Solution [riddleNumber=" + riddleNumber + ", solutionNumber=" + solutionNumber + ", result=" + result
				+ "]";
	}
	
	
	
}
