
public class DirectedEdge {

	private final int source;
	private final int destination;
	private final double weight;
	
	public DirectedEdge(int source, int destination, double weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
	
	public int from() {
		return source;
	}
	public int to() {
		return destination;
	}
	public double weight() {
		return weight;
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