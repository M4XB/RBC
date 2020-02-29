package Djikstra;

public class Algorithms {
    public Graph graph;

    public Algorithms(){

    }

    public String gibKürzestenPfad(String knotenVon, String knotenNach){       
        graph.setAllEdgeMarks(false);
        graph.setAllVertexMarks(false);
        List<Vertex> alle = graph.getVertices();
        alle.toFirst();
        while(alle.hasAccess()){
            alle.getContent().vorgänger_id = "";
            alle.getContent().distanz = Double.MAX_VALUE;
            alle.next();
        }
                
        Vertex start = graph.getVertex(knotenVon);
        start.distanz = 0;
        Vertex ziel = graph.getVertex(knotenNach);
        
        List<Vertex> suchListe = new List<Vertex>();
        suchListe.append(start);
        suchListe.toFirst();
        
        while(!suchListe.isEmpty() && !suchListe.getContent().equals(ziel)){
            suchListe.toFirst();   
            Vertex aktuell = suchListe.getContent();
            aktuell.setMark(true);
            suchListe.remove();
            List<Vertex> nachbarn = graph.getNeighbours(aktuell);
            nachbarn.toFirst();
            while(nachbarn.hasAccess()){
                Vertex nachbar = nachbarn.getContent();
                if(!nachbar.isMarked()) {
                    if(nachbar.vorgänger_id.equals("")){
                        nachbar.vorgänger_id = aktuell.getID();
                        nachbar.distanz = aktuell.distanz + graph.getEdge(aktuell, nachbar).getWeight();
                        suchListe = fügeSortiertEin(suchListe, nachbarn.getContent());
                    }
                    else{
                        suchListe.toFirst();
                        while(!suchListe.getContent().equals(nachbar)){
                            suchListe.next();
                        }
                        if(aktuell.distanz + graph.getEdge(aktuell, nachbar).getWeight() < nachbar.distanz){
                            suchListe.remove();
                            nachbar.vorgänger_id = aktuell.getID();
                            nachbar.distanz = aktuell.distanz + graph.getEdge(aktuell, nachbar).getWeight();
                            suchListe = fügeSortiertEin(suchListe, nachbarn.getContent());
                        }
                    }
                }
                nachbarn.next();
            }  
            suchListe.toFirst();
        }
        String ergebnisFalsch = knotenNach;
        Vertex wegKnoten = ziel;
        while(!wegKnoten.vorgänger_id.equals("")){
            ergebnisFalsch += wegKnoten.vorgänger_id;
            wegKnoten = graph.getVertex(wegKnoten.vorgänger_id);
        }

        String ergebnis = ""+ergebnisFalsch.charAt(ergebnisFalsch.length()-1);
        int i = ergebnisFalsch.length()-2;
        while(i >= 0){
            ergebnis += "->"+ergebnisFalsch.charAt(i);
            i--;
        }
        ergebnis +="; Länge: "+ziel.distanz;
        return ergebnis;
    }
    
    private List<Vertex> fügeSortiertEin(List<Vertex> liste, Vertex v){
        liste.toFirst();
        boolean eingefügt = false;
        while(liste.hasAccess() && eingefügt == false){
            if(liste.getContent().distanz > v.distanz){
                liste.insert(v);
            }
            liste.next();
        }
        if(eingefügt == false){
            liste.append(v);
        }
        return liste;
    }
    
    public void Breitensuche(String knotenVon){
        graph.setAllEdgeMarks(false);
        graph.setAllVertexMarks(false);
        Vertex start = graph.getVertex(knotenVon);
        Stack <Vertex> s = new Stack<Vertex>(); 
        List <Vertex> p = new List<Vertex>(); 
        s.push(start);
        start.setMark(true);
        while(!s.isEmpty()){
            Vertex k = s.top();
            this.gibStackAus(s);
            s.pop();
            p = graph.getNeighbours(k);
            p.toFirst();
            while(p.hasAccess()){

                if(!p.getContent().isMarked()){
                    p.getContent().setMark(true);
                    s.push(p.getContent());
                    p.remove();
                }
                else{
                    p.remove();
                }
            }
        }
    }
    
    public void gibStackAus(Stack <Vertex> s){
        Stack <Vertex> f = new Stack<Vertex>();
        String g = " ";
        while(!s.isEmpty()){
            f.push(s.top());
            g = g+f.top().getID()+"  ";
            s.pop();
        }
        System.out.println(g);
        while(!f.isEmpty()){
            s.push(f.top());
            f.pop();
        }
    }
}