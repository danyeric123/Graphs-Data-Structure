package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ALGraph graph = new ALGraph(false);
        System.out.println("BFS (and DFS) test (see BFSTest for graph):");
        readFromFile("/Users/davidnagar/IdeaProjects/graphs/src/com/company/BFSTest", graph);
        graph.BFS("s");
        graph.DFS();
        graph = new ALGraph(true);
        System.out.println("DFS test (see DFSTest for graph):");
        readFromFile("/Users/davidnagar/IdeaProjects/graphs/src/com/company/DFSTest", graph);
        graph.DFS();
        graph = new ALGraph(true);
        System.out.println("Dijstraka test (see DijstraTest for graph):");
        readFromFile("/Users/davidnagar/IdeaProjects/graphs/src/com/company/DijstrakaTest", graph);
        graph.Dijkstra("s");
        graph = new ALGraph(true);
        System.out.println("Max flow test (see FlowTest for graph):");
        readFromFile("/Users/davidnagar/IdeaProjects/graphs/src/com/company/FlowTest", graph);
        graph.maxFlow("a","f");
    }

    public static void readFromFile(String filepath, ALGraph alGraph){
        BufferedReader fp = null;
        try {
            fp = new BufferedReader(new FileReader(new File(filepath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fp != null) {
            try {
                String line ;
                while( (line = fp.readLine()) != null ) {
                    String[] lineArray = line.split(" ");
                    if (lineArray.length ==1){
                        alGraph.addNode(lineArray[0]);
                    }else if (lineArray.length == 2){
                        alGraph.addEdge(lineArray[0],lineArray[1]);
                    } else if (lineArray.length > 2){
                        alGraph.addEdge(lineArray[0],lineArray[1],Integer.parseInt(lineArray[2]),
                                Integer.parseInt(lineArray[2]));
                    }else {
                        fp.close();
                    }
                }
                fp.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
