import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by db on 29/05/16.
 */
public class Graph {

    private ArrayList<Node> nodes;

    public Graph(ArrayList<Integer> vertices, HashSet<Edge> edges) {
        this.nodes = new ArrayList<>();
        for(Integer vertice : vertices) {
            nodes.add(new Node(vertice, vertices.size()));
        }

        for(Edge edge: edges) {
            nodes.get(edge.getSource()).addNeighbor(edge.getDestiny());
            nodes.get(edge.getDestiny()).addNeighbor(edge.getSource());
        }
    }

    public ArrayList<Node> getNodes() {
        ArrayList<Node> res = new ArrayList<>();
        for(Node node: nodes) {
            res.add(node);
        }
        return res;
    }

    public Node getNode(int ID) {
        return nodes.get(ID).clone();

    }

    public void setNode(Integer ID, Node node) {
        nodes.set(node.getID(), node);
    }

    public void addDelivered(Integer ID, VectorClock VCm) {
        nodes.get(ID).addDelivered(VCm);
    }

    public void removeStorage(Integer ID, VectorClock VCm) {
        nodes.get(ID).removeStorage(VCm);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Node node: nodes) {
            s.append(node.toString());
            s.append("\n");
        }
        s.append("\n");

        return s.toString();
    }
}
