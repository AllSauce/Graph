import java.util.*;

/**
 * A graph with a fixed number of vertices implemented using adjacency maps.
 * Space complexity is &Theta;(n + m) where n is the number of vertices and m
 * the number of edges.
 *
 * @author Krenar Manxhuka
 * @version 2021-02-23
 */
public class Graph {
    /**
     * The map edges[v] contains the key-value pair (w, c) if there is an edge from
     * v to w; c is the cost assigned to this edge. The maps may be null and are
     * allocated only when needed.
     */
    private final Map<Integer, Integer>[] edges;

    /** Number of edges in the graph. */
    private int numEdges;
    private int numVertices;

    /**
     * Constructs a HashGraph with n vertices and no edges. Time complexity: O(n)
     *
     * @throws IllegalArgumentException if n smaller than 0
     */
    public Graph(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n = " + n);

        // The array will contain only Map<Integer, Integer> instances created
        // in addEdge(). This is sufficient to ensure type safety.
        @SuppressWarnings("unchecked")
        Map<Integer, Integer>[] a = new HashMap[n];
        for (int i = 0; i < a.length; i++) {
            a[i] = new HashMap<>();
        }
        edges = a;
        numVertices = n;
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int numVertices() {
        return numVertices;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int numEdges() {
        return numEdges;
    }


    /**
     * Returns the outdegree of vertex v.
     *
     * @param v vertex
     * @return the outdegree of vertex v
     * @throws IllegalArgumentException if v is out of range
     */
    public int degree(int v) throws IllegalArgumentException {
        if(!vertexInRange(v)){
            throw new IllegalArgumentException("Not a valid vertix");
        }
        return edges[v].size();
    }

    /**
     * Returns an iterator of vertices adjacent to v.
     *
     * @param v vertex
     * @return an iterator of vertices adjacent to v
     * @throws IllegalArgumentException if v is out of range
     */
    public Iterator<Integer> neighbors(int v) throws IllegalArgumentException{
        if(!vertexInRange(v)){
            throw new IllegalArgumentException("Vertex is out of range");
        }
        ArrayList<Integer> setOfKeys = new ArrayList<Integer>(edges[v].keySet());
        Iterator<Integer> iterator = setOfKeys.iterator();
        return iterator;
    }

    /**
     * Returns true if there is an edge (from, to).
     *
     * @param v vertex
     * @param w vertex
     * @return true if there is an edge (from, to).
     * @throws IllegalArgumentException if from or to are out of range
     */
    public boolean hasEdge(int v, int w) throws IllegalArgumentException{
      if(!vertexInRange(v) || !vertexInRange(w)){
          throw new IllegalArgumentException("Vertex is out of range");
       }
        ArrayList<Integer> setOfKeys = new ArrayList<Integer>(edges[v].keySet());
        Iterator<Integer> iter = setOfKeys.iterator();
        while(iter.hasNext()){
            if(iter.next() == w){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the edge cost if from and to are adjacent, otherwise -1.
     *
     * @param v vertex
     * @param w vertex
     * @return edge cost if available, -1 otherwise
     * @throws IllegalArgumentException if from or to are out of range
     */
    public int cost(int v, int w) throws IllegalArgumentException {
        if(!vertexInRange(v)){
          throw new IllegalArgumentException("From is out of range");
        }
        else if(!vertexInRange(w)){
          throw new IllegalArgumentException("To is out of range");
        }

        if(hasEdge(v, w)){
            return edges[v].get(w);
        }
        else if(hasEdge(w, v)){
          return edges[w].get(v);
        }
        return -1;
    }

    /**
     * Inserts an edge with edge cost c.
     *
     * @param c edge cost, c >= 0
     * @param v vertex
     * @param w vertex
     * @throws IllegalArgumentException if from or to are out of range
     */
    public void add(int v, int w, int c) throws IllegalArgumentException{
      if(!vertexInRange(v) || !vertexInRange(w)){
          throw new IllegalArgumentException("Vertex is out of range");
      }
      else if(!hasEdge(v, w)){
        edges[v].put(w, c);
        edges[w].put(v, c);
        numEdges++;
      }
      else if(newCost(v, c)){
        edges[v].put(w, c);
        edges[w].put(v, c);
      }
    }

    /**
     * Removes the edges between v and w.
     *
     * @param v vertex
     * @param w vertex
     * @throws IllegalArgumentException if v or w are out of range
     */
    public void remove(int v, int w) throws IllegalArgumentException{
      if(!vertexInRange(v) || !vertexInRange(w)){
          throw new IllegalArgumentException("Vertex is out of range");
      }
      else if(hasEdge(v, w)){
        edges[v].remove(w);
        edges[w].remove(v);
        numEdges--;
      }
    }

    /**
     * Returns a string representation of this graph.
     *
     * Should represent the graph in terms of edges (order does not matter). For
     * example, if a graph has edges (2,3,0) and (1,0,0), the
     * representaiton should be:
     *
     * "{(1,0,0), (2,3,0)}" or "{(2,3,0), (1,0,0)}"
     *
     * An empty graph is just braces:
     *
     * "{}"
     *
     * @return a String representation of this graph
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("{");
        int count = 0;
        for(int i = 0; i < edges.length; i++){
            for(Integer key : edges[i].keySet()){
              String k = String.valueOf(key);
              if(s.toString().contains(k)){
                continue;
              }
              else if(!s.toString().contains(k)){
                s.append("(");
                s.append(i + "," + key + "," + edges[i].get(key));
                s.append(")");
                count++;
                if(count > 0){
                  s.append(", ");
                }
              }
           }
        }
        if(count > 0){
          s.delete(s.length() -2, s.length());
        }
        s.append("}");
        return s.toString();
    }

    /*
     * Checks if the vertex is in the correct range.
     *
     * @return true if vertex is in range, and false if not.
     */
    public boolean vertexInRange(int vertex){
        if(vertex >= numVertices || vertex < 0) return false;
        return true;
    }

    private boolean newCost(int from, int cost){
      if(edges[from].containsValue(cost)) return false;
      return true;
    }
}
