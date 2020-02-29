package Djikstra;


public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;
  
    //Creates a new object of a graph
    public Graph(){
      //creates empty lists for vertices and edges
      vertices = new List<Vertex>();
      edges = new List<Edge>();
    }
  
    //Returns a new list of all vertices
    public List<Vertex> getVertices(){
      //Creates new list of all vertices
      List<Vertex> result = new List<Vertex>();
      vertices.toFirst();
      while (vertices.hasAccess()){
        result.append(vertices.getContent());
        vertices.next();
      }
      //Moves current element to the beginning
      result.toFirst();
      return result;
    }
  
    //Returns a new list of all edges
    public List<Edge> getEdges(){
      //Creates new list of all edges
      List<Edge> result = new List<Edge>();
      edges.toFirst();
      while (edges.hasAccess()){
        result.append(edges.getContent());
        edges.next();
      }
      //Moves current element to the beginning
      result.toFirst();
      return result;
    }
  
    //Returns vertex with the delivered id
    //If the vertex is not part of the graph null is returned
    public Vertex getVertex(String pID){
      //Vertex-Objekt mit pID als ID suchen.
      Vertex result = null;
      vertices.toFirst();
      while (vertices.hasAccess() && result == null){
        if (vertices.getContent().getID().equals(pID)){
          result = vertices.getContent();
        }
        vertices.next();
      }
      return result;
    }
  
    //Adds vertex to the graph if not already exists
    public void addVertex(Vertex pVertex){
      //Checks if vertex exists and its id
      if (pVertex != null && pVertex.getID() != null) {
        //Checks if a vertex with this iod already exists in the graph
        boolean freeID = true;
        vertices.toFirst();
        while (vertices.hasAccess() && freeID){
          if (vertices.getContent().getID().equals(pVertex.getID())){
            freeID = false;
          }
          vertices.next();
        }
        //If id is free, vertext is added
        if (freeID) {
          vertices.append(pVertex);      
        }
      }
    }
  
    //Adds edge to graph if not already exists
    public void addEdge(Edge pEdge){ 
      //Checks if edge exists
      if (pEdge != null){  
        Vertex[] vertexPair = pEdge.getVertices();
        
        //Checks criteria for adding
        if (vertexPair[0] != null && vertexPair[1] != null && 
        this.getVertex(vertexPair[0].getID()) == vertexPair[0] && 
        this.getVertex(vertexPair[1].getID()) == vertexPair[1] &&
        this.getEdge(vertexPair[0], vertexPair[1]) == null && 
        vertexPair[0] != vertexPair[1]){
          //Adds edge
          edges.append(pEdge); 
        }
      }
    }
  
    //Removes vertex and its edges, if exists
    public void removeVertex(Vertex pVertex){
      //removes edges
      edges.toFirst();
      while (edges.hasAccess()){
        Vertex[] akt = edges.getContent().getVertices();
        if (akt[0] == pVertex || akt[1] == pVertex){
          edges.remove();
        } else {
          edges.next();
        }
      }
  
      //Removes vertices
      vertices.toFirst();
      while (vertices.hasAccess() && vertices.getContent()!= pVertex){
        vertices.next();
      }
      if (vertices.hasAccess()){
        vertices.remove();
      }
    }
  
    //Removes edge if exists
    public void removeEdge(Edge pEdge){
      //removes edges
      edges.toFirst();
      while (edges.hasAccess()){
        if (edges.getContent() == pEdge){
          edges.remove();
        } else {
          edges.next();
        }
      }
    }
  
    //Sets mark of all vertices
    public void setAllVertexMarks(boolean pMark){
      vertices.toFirst();
      while (vertices.hasAccess()){
        vertices.getContent().setMark(pMark);
        vertices.next();
      }
    }
  
    //Sets mark of all edge
    public void setAllEdgeMarks(boolean pMark){
      edges.toFirst();
      while (edges.hasAccess()){
        edges.getContent().setMark(pMark);
        edges.next();
      }
    }
  
    //Return true if all verices are marked, else false
    public boolean allVerticesMarked(){
      boolean result = true;
      vertices.toFirst();
      while (vertices.hasAccess()){
        if (!vertices.getContent().isMarked()){
          result = false;
        }
        vertices.next();
      }
      return result;
    }
  
    //Return true if all edges are marked, else false
    public boolean allEdgesMarked(){
      boolean result = true;
      edges.toFirst();
      while (edges.hasAccess()){
        if (!edges.getContent().isMarked()){
          result = false;
        }
        edges.next();
      }
      return result;
    }
  
    //Retusn all neighbours of the vertex
    public List<Vertex> getNeighbours(Vertex pVertex){
      List<Vertex> result = new List<Vertex>();
      //Loop over all edges
      edges.toFirst();
      while (edges.hasAccess()){
        
        Vertex[] vertexPair = edges.getContent().getVertices();
        if (vertexPair[0] == pVertex) {
          result.append(vertexPair[1]);
        } else { 
          if (vertexPair[1] == pVertex){
            result.append(vertexPair[0]);
          }
        }
        edges.next();
      }    
      return result;
    }
  
    //Return all edges to the given vertex
    public List<Edge> getEdges(Vertex pVertex){
      List<Edge> result = new List<Edge>();
      //Loop over all edges
      edges.toFirst();
      while (edges.hasAccess()){
        
        Vertex[] vertexPair = edges.getContent().getVertices();
        if (vertexPair[0] == pVertex) {
          result.append(edges.getContent());
        } else{ 
          if (vertexPair[1] == pVertex){
            result.append(edges.getContent());
          }
        }
        edges.next();
      }    
      return result;
    }
  
    //Returns edge which connects the both given vertices
    public Edge getEdge(Vertex pVertex, Vertex pAnotherVertex){
      Edge result = null;
      edges.toFirst();
      while (edges.hasAccess() && result == null){
        
        Vertex[] vertexPair = edges.getContent().getVertices();
        if ((vertexPair[0] == pVertex && vertexPair[1] == pAnotherVertex) ||
        (vertexPair[0] == pAnotherVertex && vertexPair[1] == pVertex)) {
          result = edges.getContent();
        } 
        edges.next();
      }    
      return result;
    }
  
    //Return if graph has vertices
    public boolean isEmpty(){
      return vertices.isEmpty();
    } 
  }