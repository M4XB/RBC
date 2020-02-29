public class Vertex{
    private String id;
    private boolean mark;
    
    public String vorgänger_id;
    public double distanz;
    
    //Creates a new Object of a vertex with the mark false
    public Vertex(String pID){
      id = pID;
      mark = false;
    }
    
    //Returns the id of the vertex as a String
    public String getID(){
      return new String(id);
    }
    
    //Sets the mark of the vertex
    public void setMark(boolean pMark){
      mark = pMark;
    }
    
    //Returns true if mark is true, else false
    public boolean isMarked(){
      return mark;
    }
    
  }