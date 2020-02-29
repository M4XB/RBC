package Djikstra;

public class Edge{
    private Vertex[] vertices;
    private double weight;
    private boolean mark;
    
    /**
     * Creates a new object of edge
     * @param pVertex first vertex, the edge connects
     * @param pAnotherVertex second vertex, the edge connects
     * @param pWeight weight of the edge
     */
    public Edge(Vertex pVertex, Vertex pAnotherVertex, double pWeight){
      vertices = new Vertex[2];
      vertices[0] = pVertex;
      vertices[1] = pAnotherVertex;
      weight = pWeight;
      mark = false;
    }
    
    //Returns vertices, the edge connects
    public Vertex[] getVertices(){
      Vertex[] result = new Vertex[2];
      result[0] = vertices[0]; 
      result[1] = vertices[1];
      return result;
    }
    
    //Sets weight of the edge
    public void setWeight(double pWeight){
      weight = pWeight;
    } 
    
    //Returns weight of the edge
    public double getWeight(){
      return weight;
    } 
    
    //Sets the mark of the edge
    public void setMark(boolean pMark){
      mark = pMark;
    }
    
    //Returns true if mark is true, else false
    public boolean isMarked(){
      return mark;
    }
  }