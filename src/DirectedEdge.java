public class DirectedEdge {

	private int source;
	private int destination;
	private double weight;
	private double countSP;
	
	public DirectedEdge(int source, int destination, double weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
	
	public int from() {
		return this.source;
	}
	public int to() {
		return this.destination;
	}
	public double weight() {
		return this.weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getCountSP() {
		return this.countSP;
	}
	public void addToCountSP(double score){
		this.countSP += score;
	}
	
	@Override
	public String toString() {
		
		String str;
		if (this.weight == 1) {
			str = this.destination + "";
		} else {
			str = this.source + "/" + this.destination + "/" + this.weight;
		}
		return str;
	}
}