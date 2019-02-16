package Model;

public class Miner {

	// -------------------------------Class  Members------------------------------
		
		private String uniqueAddress;
		private String name;	
		private String password;
		private double digitalProfit; 
		private String email;


		// -------------------------------Constructor------------------------------
		

		public Miner(String uniqueAddress, String name, String password, double digitalProfit, String email) {
			this.uniqueAddress = uniqueAddress;
			this.name = name;
			this.password = password;
			this.digitalProfit = digitalProfit;
			this.email = email;
		}
		
		public Miner(String uniqueAddress) {
			this.uniqueAddress = uniqueAddress;
		}
		
		// -------------------------------Getters And Setters------------------------------
		
		public String getUniqueAddress() {
			return uniqueAddress;
		}

		public void setUniqueAddress(String uniqueAddress) {
			this.uniqueAddress = uniqueAddress;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public double getDigitalProfit() {
			return digitalProfit;
		}

		public void setDigitalProfit(double digitalProfit) {
			this.digitalProfit = digitalProfit;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
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
			Miner other = (Miner) obj;
			if (uniqueAddress == null) {
				if (other.uniqueAddress != null)
					return false;
			} else if (!uniqueAddress.equals(other.uniqueAddress))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Miner's Unique Address: " + uniqueAddress + ", Name: " + name + ", Digital Profit: " + digitalProfit + ", Email: " + email + "";
		}
		
		
}
