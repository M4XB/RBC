import java.awt.* ;


/** A rover that looks before it moves. */
public class RoMax_Roamer extends Creature {
	
	// array containing map data
	private int[][] staticMap; 
	private int[][] roverMap; 
	
    @Override
	public void run() {
    	
    	// get dimension to generate map
    	Dimension mapSize = getMapDimensions();
    	// generate map
    	int mh = mapSize.height;
    	int mw = mapSize.width;
    	
    	staticMap = new int[mh][mw];
    	roverMap = new int[mh][mw];
    	
    	// display map
    	printOutMap(staticMap, mh, mw);
    	
    	//observe roamer position
        Observation self = observeSelf();
        
        while (true) {
        	
        	// show Rover position
            System.out.println("roamer is at " + " x=" + self.position.y +" y=" + self.position.x);
            roverMap[self.position.y][self.position.x] = 1;
        	
        	//observe space in front
        	Observation obs = observe()[0];
        	
            // save & log observed position
            System.out.println("observed " + obs.type + " at" + " x=" + obs.position.y +" y=" + obs.position.x);
            
            // if WALL is observed, save WALL position in staticMap
            if (obs.type == Type.WALL) {
            	staticMap[obs.position.y][obs.position.x] = 1;
            }
            // display static Maps
            printOutMap(staticMap, mh, mw);
            
            // get distance to observed position
            int d = distance(obs.position) - 1;
            System.out.println("moving " + d  + " units towards "+ getDirection() );
           
            // Move until the far edge of observed position
            for (int i = 0; i < d; ++i) {
                
            	// if something stops the movement before arrived at observed position
            	if (! moveForward()) {
            		System.out.println("movement stopped by unexpected obstacle");
            		
                    // attack unobserved obstacle
                    attack();
                    break;
                }
            }
            
            // if enemy is observed
            if (isEnemy(obs)) {
                // Attack Enemy
            	System.out.println("Leeeeroy Jenkins");
                attack();
            }
            
            // after arriving at observed position, change direction
            System.out.println("turning right");
            turnRight();
        }
    }
    
    
    //Prints out up-to-date map in the console
    private void printOutMap(int map[][], int height, int width) {
    	for (int i=0; i<height; i++) {
    		for (int y=0; y<width; y++) {

    			System.out.print(map[i][y]);
    			System.out.print(" ");
    		}
    		System.out.println();
    	}
    }
    
    @Override
	public String getAuthorName() {
        return "Darwin SDK";
    }

    @Override
	public String getDescription() {
        return "A rover that looks before it moves.";
    }
}
