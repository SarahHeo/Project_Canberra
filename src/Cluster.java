import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private final List<Integer> nodes = new ArrayList<Integer>();

    public void addNode(int node) {
    	nodes.add(node);
    }
    
    public List<Integer> getNodes() {
    	return nodes;
    }

}