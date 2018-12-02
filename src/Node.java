import java.util.ArrayList;

/**
 * Created by db on 29/05/16.
 */
public class Node {

    private final int ID;
    private ArrayList<Integer> neighbors;
    private VectorClock VC;
    private ArrayList<VectorClock> storage;
    private ArrayList<VectorClock> delivered;


    public Node(int ID, int total) {
        this.ID = ID;
        this.neighbors = new ArrayList<>();
        this.VC = new VectorClock(total);
        this.storage = new ArrayList<>();
        this.delivered = new ArrayList<>();
    }

    public Node(Node node) {
        this.ID = node.getID();
        this.neighbors = node.getNeighbors();
        this.VC = node.getVC();
        this.storage = node.getStorage();
        this.delivered = node.getDelivered();
    }

    public Node clone() {
        return new Node(this);
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Integer> getNeighbors() {
        ArrayList<Integer> res = new ArrayList<>();
        for(Integer neighbor: neighbors) {
            res.add(neighbor);
        }
        return res;
    }

    public void incVectorClock(int ID) {
        VC.incVectorClock(ID);
    }

    public VectorClock getVC() {
        return VC.clone();
    }

    public int getValueVC(int ID) {
        return VC.getValue(ID);
    }

    public ArrayList<VectorClock> getStorage() {
        ArrayList<VectorClock> res = new ArrayList<>();
        for(VectorClock VC: storage) {
            res.add(VC);
        }
        return res;
    }

    public ArrayList<VectorClock> getDelivered() {
        ArrayList<VectorClock> res = new ArrayList<>();
        for(VectorClock VC: delivered) {
            res.add(VC);
        }
        return res;
    }

    public void addStorage(VectorClock VC) {
        storage.add(VC);
    }

    public void removeStorage(VectorClock VC) { storage.remove(VC); }

    public void addDelivered(VectorClock VC) {
        for(VectorClock vc: delivered) {
            if(vc.equals(VC)) {
                return;
            }
        }
        delivered.add(VC);
    }

    public void addNeighbor(Integer neighbor) {
        this.neighbors.add(neighbor);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(ID + "-> ");

        for(Integer neighbor: neighbors) {
            s.append(neighbor + " ");
        }
        s.append("\nVC: "+ VC.toString());
        s.append("\nST: "+ storage.toString());
        s.append("\nDV: "+ delivered.toString());

        return s.toString();
    }
}
