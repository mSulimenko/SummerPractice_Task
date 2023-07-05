import java.util.*;

public class Solve {
    private Map<String, List<Node>> graph;
    private String startNode;
    private String endNode;
    private boolean isPathFound;
    private String path;

    public Solve() {
        this.graph = new HashMap<>();
        this.startNode = null;
        this.endNode = null;
        this.isPathFound = false;
        this.path = null;
    }

    // Вспомогательный класс для представления узла графа
    private static class Node {
        private String nodeName;
        private double weight;

        public Node(String nodeName, double weight) {
            this.nodeName = nodeName;
            this.weight = weight;
        }

        public String getNodeName() {
            return nodeName;
        }

        public double getWeight() {
            return weight;
        }
    }

    // Выводит ответ в нужном формате
    private void printFinalPath() {
        System.out.println(path);
    }

    // Считывает входные данные и составляет граф в виде словаря
    private void readInput() {
        Scanner scanner = new Scanner(System.in);
        startNode = scanner.next();
        endNode = scanner.next();
        scanner.nextLine(); // Считывание оставшейся части строки
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            String[] tokens = line.split(" ");
            String fromNode = tokens[0];
            String inNode = tokens[1];
            double weight = Double.parseDouble(tokens[2]);
            if (graph.containsKey(fromNode)) {
                graph.get(fromNode).add(new Node(inNode, weight));
            } else {
                List<Node> nodeList = new ArrayList<>();
                nodeList.add(new Node(inNode, weight));
                graph.put(fromNode, nodeList);
            }
        }
        scanner.close();
    }

    // Сортирует значения по весу ребер
    private void sortKeysInDict() {
        for (String key : graph.keySet()) {
            List<Node> nodeList = graph.get(key);
            nodeList.sort(Comparator.comparingDouble(Node::getWeight));
        }
    }

    // Инициализирует решение жадным алгоритмом
    private void startGreedyAlgorithm() {
        readInput();
        sortKeysInDict();
        iterateGreedyAlgorithm(startNode, startNode);
    }

    // Строит путь жадным алгоритмом
    private void iterateGreedyAlgorithm(String currentNode, String currentPath) {
        if (isPathFound) {
            return;
        }
        if (currentNode.equals(endNode)) {
            path = currentPath;
            isPathFound = true;
            return;
        }
        if (graph.containsKey(currentNode)) {
            List<Node> nodeList = graph.get(currentNode);
            for (Node node : nodeList) {
                String nextNode = node.getNodeName();
                String nextPath = currentPath + nextNode;
                iterateGreedyAlgorithm(nextNode, nextPath);
            }
        }
    }



    public static void main(String[] args) {
        Solve solution = new Solve();
        solution.startGreedyAlgorithm();
        solution.printFinalPath();
    }
}
