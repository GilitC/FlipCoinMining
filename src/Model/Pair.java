package Model;

public class Pair implements Comparable<Pair> {
	private Transaction transaction1;
	private Transaction transaction2;
	
	public Pair(Transaction transaction1 , Transaction transaction2) {
		this.transaction1=transaction1;
		this.transaction2=transaction2;
	}
	
	public Transaction getTransaction1() {
		return this.transaction1;
	}
	
	public Transaction getTransaction2() {
		return this.transaction2;
	}
	@Override
	public int compareTo(Pair p) {
		double fee1=this.getTransaction1().getFee()+this.getTransaction2().getFee();
		double fee2 = p.getTransaction1().getFee()+p.getTransaction2().getFee();
		return Double.compare(fee2, fee1);
	}
	

	@Override
	public String toString() {
		return "Pair: Profit:  "+ (this.transaction1.getFee() +this.transaction2.getFee())  + "\n[Transaction 1=" + this.transaction1.toString() + "\n "
				+ " Transaction 2=" + this.transaction2.toString() 
				+ "]\n";
	}

}
