import java.util.*;

/**
 * Class for solving graph problems.
 *
 * @author [Name]
 * @version [Date]
 */
public class GraphAlgorithms {

    /**
    * Determines if a component has a cycle.
    *
    * @param g the graph to search
    * @param v the node to start at
    * @param visited boolean array keeping track of visited nodes
    * @param parent parent node of v
    * @return true if component has cycle, false otherwise
    */
    private static boolean DFS(Graph g, int v, boolean[] visited, int parent) {
        visited[v] = true;

        Iterator<Integer> iterator = g.neighbors(v);
        while(iterator.hasNext()){
          int i = iterator.next();

          if(!visited[i])
          {
            if(DFS(g, i, visited, v)) return true;
          }

          else if(i != parent) return true;

          iterator.remove();
        }
      return false;
    }

    /**
    * Determine if there is a cycle in the graph. Implement using DFS.
    *
    * @param g the graph to search
    * @return true if there exists at least one cycle in the graph, false otherwise
    */
    public static boolean hasCycle(Graph g) {
       boolean visited[] = new boolean[g.numVertices()];

       //Call recursive to detect cycles
       for(int i = 0; i < visited.length; i++){

         if(!visited[i]){
           if(DFS(g, i, visited, i)) return true;
         }
       }
       return false;
    }

    /**
    * Determine if there is a path between two vertices. Implement using
    * BFS.
    *
    * @param v vertex
    * @param w vertex
    * @param g the graph to search
    * @return true if there is a path between v and w, false otherwise
    */
    public static boolean hasPath(Graph g, int v, int w) {
        int currVertex = v;
        boolean[] visited = new boolean[g.numVertices()];
        Queue<Integer> queue = new LinkedList<Integer>();

        visited[currVertex] = true;
        queue.add(currVertex);

        while(!queue.isEmpty())
        {
          queue.remove();

          for(Iterator<Integer> iterator = g.neighbors(currVertex); iterator.hasNext();)
          {
            int nextVert = iterator.next();
            if(currVertex == w) return true;

            else if(!visited[nextVert])
            {
              queue.add(nextVert);

              visited[nextVert] = true;
            }

            iterator.remove();
          }
          
          if(!queue.isEmpty()){
            currVertex = queue.peek();
          }
       }

        return false;
    }

    /* Returns the start of the graph */
    private static Integer startOf(Graph g){
        return 0;
    }
}
