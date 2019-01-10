package Model;

public class RiddleLevel {

	private int levelCode;
	private String levelName;
	private int levelDifficulty;
	private int blockSize;
	
	
	public RiddleLevel(int levelCode, String levelName, int levelDifficulty, int blockSize) {
		
		this.levelCode = levelCode;
		this.levelName = levelName;
		this.levelDifficulty = levelDifficulty;
		this.blockSize = blockSize;
	}


	public int getLevelCode() {
		return levelCode;
	}


	public void setLevelCode(int levelCode) {
		this.levelCode = levelCode;
	}


	public String getLevelName() {
		return levelName;
	}


	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}


	public int getLevelDifficulty() {
		return levelDifficulty;
	}


	public void setLevelDifficulty(int levelDifficulty) {
		this.levelDifficulty = levelDifficulty;
	}


	public int getBlockSize() {
		return blockSize;
	}


	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + levelCode;
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
		RiddleLevel other = (RiddleLevel) obj;
		if (levelCode != other.levelCode)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "RiddleLevel [levelCode=" + levelCode + ", levelName=" + levelName + ", levelDifficulty="
				+ levelDifficulty + ", blockSize=" + blockSize + "]";
	}
	
	
	
	
}
