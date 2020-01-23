import java.awt.* ;


/** A rover that looks before it moves. */
public class RoMax_Roamer extends Creature {
	
	// array containing static obstacles
	private int[][] staticMap; 
	
	// array containing rover positions
	private int[][] roverMap; 
	
	// observe roamer position
    Observation self;
    Observation obs;
	
    // report roamer position
    public void reportRoverPosition() {
    	self = observeSelf();
    	roverMap[self.position.y][self.position.x] = 1;
    };
	
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
    	
    	
        while (true) {
        	
        	// get rover position
        	reportRoverPosition();
        	// show Rover position
            System.out.println("roamer is at " + " x=" + self.position.x +" y=" + self.position.y);
        	
        	//observe space in front
        	obs = observe()[0];
        	
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
            	
            	reportRoverPosition();
                
            	// if something stops the movement before arrived at observed position
            	if (! moveForward()) {
            		System.out.println("movement stopped by unexpected obstacle");
            		
                    // attack unobserved obstacle
                    attack();
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
            
            // check for viable directions
            
            	// don't move into a wall
            
            	// if other options exists don't go in already explored regions
            turnRight();
        }
    }

	//Prints out up-to-date map in the console
    private void printOutMap(int map[][], int height, int width) {
    	for (int y=0; y < height; y++) {
    		for (int x=0; x<width; x++) {
    			
     			if (roverMap[y][x] == 0) {
	    			
     				switch(map[y][x]) {
	    			
	    			case 0:
	    				// EMPTY
	    				System.out.print("?");
	    				break;
	    			case 1:
	    				// WALL
	    				System.out.print("#");
	    				break;
	    			};
    			}
    			else {
        			// show previous rover positions on map
    				System.out.print("*");
    			}
    			System.out.print(" ");
    		}
			System.out.println();
    	}	
    };
    
    // turn rover towards a given direction
	//Direction currentDir = getDirection();
	//Direction targetDir;
    //private void rotate(Direction targetDir) {
    //	if (currentDir == targetDir) {	
    	//}
    	//else {
	//
    //	}
    //}
    
    
    @Override
	public String getAuthorName() {
        return "Darwin SDK";
    }

    @Override
	public String getDescription() {
        return "A rover that looks before it moves.";
    }
}
