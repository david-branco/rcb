import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by db on 29/05/16.
 */
public class RCB {

    private static Graph graph;
    private static ArrayList<Integer> sendersQueue;

    public static void initGraph(int ID) {
        if (ID == 1) {
            ArrayList<Integer> vertices = new ArrayList<>();
            for(int id = 0; id < 4; id++) {
                vertices.add(id);
            }
            HashSet<Edge> edges = new HashSet<>();
            edges.add(new Edge(0, 1)); edges.add(new Edge(0, 2));
            edges.add(new Edge(3, 1)); edges.add(new Edge(3, 2));

            graph = new Graph(vertices, edges);
        }
        else if(ID == 2) {
            ArrayList<Integer> vertices = new ArrayList<>();
            for(int id = 0; id < 9; id++) {
                vertices.add(id);
            }
            HashSet<Edge> edges = new HashSet<>();
            edges.add(new Edge(0, 1)); edges.add(new Edge(0, 3));
            edges.add(new Edge(1, 4)); edges.add(new Edge(1, 2));
            edges.add(new Edge(2, 5)); edges.add(new Edge(3, 6));
            edges.add(new Edge(3, 4)); edges.add(new Edge(4, 5));
            edges.add(new Edge(4, 7)); edges.add(new Edge(5, 8));
            edges.add(new Edge(6, 7)); edges.add(new Edge(7, 8));

            graph = new Graph(vertices, edges);
        }
        else {
            System.out.println("Invalid ID Graph\n");
            System.exit(1);
        }
    }

    public static VectorClock createMessage(Integer ID) {
        Node sender = graph.getNode(ID);
        sender.incVectorClock(ID);
        sender.addDelivered(sender.getVC());
        graph.setNode(ID, sender);

        return sender.getVC();
    }

    public static void broadcast(Integer starterNodeID, VectorClock VCm) {
        sendersQueue = new ArrayList<>();
        sendersQueue.add(starterNodeID);
        sendersQueue.add(starterNodeID);
        System.out.println(sendersQueue.toString());
        System.out.println(graph.toString());

        //While the sendersQueue as more than 1 member (the creator), the message will be forwarded.
        while(sendersQueue.size() > 1) {
            System.out.println(sendersQueue.toString());
            Integer nodeID = sendersQueue.get(1);
            sendersQueue.remove(nodeID);

            sendMessageNeighbors(sendersQueue.get(0), nodeID, VCm);
            System.out.println(graph.toString()+"\n\n");
        }
        System.out.println(graph.toString());
        sendersQueue.clear();
    }

    public static void sendMessageNeighbors(Integer creatorID, Integer forwarderID, VectorClock VCm) {
        if(graph.getNode(forwarderID).getStorage().contains(VCm)) {
            sendMessage(creatorID, forwarderID, VCm, forwarderID);
        }

        for(Integer neighborID: graph.getNode(forwarderID).getNeighbors()) {
            sendMessage(creatorID, forwarderID, VCm, neighborID);
        }
    }

    public static void sendMessage(Integer creatorID, Integer senderID, VectorClock VCm, Integer receiverID) {
        System.out.println("Message from "+senderID +" to "+ receiverID);
        Node receiver = graph.getNode(receiverID);
        VectorClock VCr = receiver.getVC();

        if(VCm.getValue(creatorID) > VCr.getValue(creatorID)) {
            for(int pos = 0; pos < VCm.getSize(); pos++) {
                if(pos != creatorID && VCm.getValue(pos) < VCr.getValue(pos)) {
                    receiver.addStorage(VCm);
                    graph.setNode(receiverID, receiver);
                    return;
                }
            }

            System.out.println("Delivered");
            receiver.incVectorClock(creatorID);
            receiver.addDelivered(VCm);
            if(receiver.getStorage().contains(VCm)) {
                receiver.removeStorage(VCm);
            }
            graph.setNode(receiverID, receiver);

            if(!sendersQueue.contains(receiverID)) {
                sendersQueue.add(receiverID);
            }
        }
    }

    public static void main(String args[]) {
        initGraph(2);

        int starterNode = 0;
        VectorClock VCm = createMessage(starterNode);
        broadcast(starterNode, VCm);

        starterNode = 1;
        VectorClock VCm2 = createMessage(starterNode);
        broadcast(starterNode, VCm2);
    }
}
