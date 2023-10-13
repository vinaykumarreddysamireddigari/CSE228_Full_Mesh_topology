import java.util.*;

class WorkStation {
    private String name;
    private String ipAddress;
    private Set<WorkStation> neighbors;

    public WorkStation(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.neighbors = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Set<WorkStation> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(WorkStation neighbor) {
        neighbors.add(neighbor);
        neighbor.neighbors.add(this);
    }
}

class Graph {
    private Map<String, WorkStation> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public void addNode(String name, String ipAddress) {
        if (nodes.containsKey(ipAddress)) {
            System.out.println("IP address already exists. Please choose a different IP address.");
        } else {
            WorkStation newNode = new WorkStation(name, ipAddress);
            for (WorkStation node : nodes.values()) {
                node.addNeighbor(newNode);
            }
            nodes.put(ipAddress, newNode);
            System.out.println("Node '" + name + "' with IP address " + ipAddress + " added to the network.");
        }
    }

    public Map<String, WorkStation> getNodes() {
        return nodes;
    }
}

class BFSReader {
    private Graph graph;

    public BFSReader(Graph graph) {
        this.graph = graph;
    }

    public void bfsRead(String ipAddress) {
        if (graph != null && graph.getNodes().containsKey(ipAddress)) {
            WorkStation startNode = graph.getNodes().get(ipAddress);
            Queue<WorkStation> queue = new LinkedList<>();
            Set<WorkStation> visited = new HashSet<>();
            queue.add(startNode);

            while (!queue.isEmpty()) {
                WorkStation node = queue.poll();
                if (!visited.contains(node)) {
                    visited.add(node);
                    System.out.println("Node: " + node.getName() + ", IP: " + node.getIpAddress());

                    for (WorkStation neighbor : node.getNeighbors()) {
                        queue.add(neighbor);
                    }
                }
            }
        } else {
            System.out.println("Node with the provided IP address not found in the network.");
        }
    }
}

public class FullMeshNetwork {
    public static void main(String[] args) {
        Graph network = new Graph();
        BFSReader bfsReader = new BFSReader(network);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Node");
            System.out.println("2. Connection Test");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter the node name: ");
                String name = scanner.nextLine();
                System.out.print("Enter the IP address: ");
                String ipAddress = scanner.nextLine();
                network.addNode(name, ipAddress);
            } else if (choice.equals("2")) {
                System.out.print("Enter the IP address of the node for connection test: ");
                String ipAddress = scanner.nextLine();
                if (network.getNodes().containsKey(ipAddress)) {
                    bfsReader.bfsRead(ipAddress);
                } else {
                    System.out.println("Node with the provided IP address not found in the network.");
                }
            } else if (choice.equals("3")) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
