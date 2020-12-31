
public class DirectedEdge {

	private int source;
	private int destination;
	private double weight;
	
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
	
	@Override
	public String toString() {
		
		String str;
		if (this.weight == 1) {
			str = this.source + "";
		} else {
			str = this.source + "/" + this.destination + "/" + this.weight;
		}
		return str;
	}
}