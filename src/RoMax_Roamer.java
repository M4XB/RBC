import java.awt.* ;

public class RoMax_Roamer extends Creature {
	
	// set this to false to disable extended console logging & save resources
	private boolean debugMode = false;
	
	private int[][] staticMap;
	private int[][] roverMap;
	
	int mapWidth;
	int mapHeight;
	
	Observation obs;
	Observation self;
	
    @Override
	public void run() {
    	
    	mapGen();
    	printMap();
    	
    	easyLogic();
    	
    }
    
    // generates maps, sets global map size
    private void mapGen() {
    	Dimension mapSize = getMapDimensions();
    	mapWidth = mapSize.width;
    	mapHeight = mapSize.height;
    	staticMap = new int[mapHeight][mapWidth];
    	roverMap = new int[mapHeight][mapWidth];
    	
    	
    	//fill static map at edge with wall
    	for (int y=0; y< mapHeight; y++) {
    		staticMap[y][0] = 1;
    		staticMap[y][mapWidth-1] = WALL_CLASS_ID;
    	}
    	for (int x=0; x< mapWidth; x++) {
    		staticMap[0][x] = 1;
    		staticMap[mapHeight-1][x] = WALL_CLASS_ID;
    	}
    	
    }
    
	//#region " Main logic functions"    
	
  	//Left hand on the wall principle    
    private void easyLogic() {    	
    	
    	if (isObjectInFrontOfYou(WALL_CLASS_ID)) {
    		turnRight();
    	}else {
    		move(distance(obs.position)-1);
    		turnRight();
    	}
    	while(true) {
    		
    		//When the rover sees the treasure, its going to hit it
    		seeTreasure();
    		if (moveForward()) {
    			reportRoverPos();
    			turnLeft();
    			
    			if (isObjectInFrontOfYou(WALL_CLASS_ID)) {
    				turnRight();
        			if (isObjectInFrontOfYou(WALL_CLASS_ID)) {
        				turnRight();
        			}	
        		}else if (!isObjectInFrontOfYou(WALL_CLASS_ID)) {
        			move(1);
        		}else {
    				turnLeft();
    			}
    		}else {
    			turnLeft();
    			if (!isObjectInFrontOfYou(WALL_CLASS_ID)) {
    				move(1);
    			}else {
    				turnLeft();
    				turnLeft();
    			}
    		}
    	}
    }
    //#endregion


//#region "Observing the area around the rover"
   
    //Returns an object of th class the rover is looking at and fills static map
    private Observation observeObject() {
    	obs = observe()[0];
    	staticMap[obs.position.y][obs.position.x] = obs.classId;
    	return obs;
    }
	
	//Returns an object of the current position of the rover
    private Observation observateSelf() {
    	self = observeSelf();
    	return self;
    }
    
    //checks if rover is standing in front of the object
    private boolean isObjectInFrontOfYou(int classId) {
		Observation observe = observeObject();
		if (observe.classId == classId && distance(observe.position) == 1) {
    		return true;
    	} else {
    		return false;
    	}
	}
	
    //checks if rover is seeing the treasure
    private void seeTreasure() {
		Observation possibleTreasure = observeObject();
    	if (possibleTreasure.classId  == TREASURE_CLASS_ID) {
    		moveForward(distance(obs.position)-1);
			attack();
    	}
    }
    
    //lets the rover move and updates the rover map
    private void move(int distance) {
    	for (int i = 0; i < distance; i++) {
    		moveForward(1);
        	reportRoverPos();
    	}
    }
    
    //checks if enemy is in range to attack
    private void enemyInSight(){	
		Observation enemy = observeObject();
    	int enemyDistance = distance(enemy.position);
		if(enemyDistance == 2){
			delay();
		} else if(enemyDistance == 1){
			attack();
		}else{
			moveForward();
			reportRoverPos();
		}
    }
    
    //checks if rover was at the delivered position
    private boolean isNextPositionKnown(Point position) {
    	if (roverMap[position.y][position.x] == 1) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //checks if rover has run in a circle
    private boolean haveRunInCircle() {
    	boolean res = false;
    	
    	return res;
    }
    
//#endregion
	

//#region "Printing stuff to the console"
    
    //debug logger
    public void log(String msg) {
    	if (debugMode) {
    	System.out.println(msg);
    	System.out.println("");
    	}
    }
    
    //Prints out up-to-date map in the console
    private void printMap() {
    	System.out.println("");
    	for (int y=0; y<mapHeight; y++) {
    		for (int x=0; x<mapWidth; x++) {
    			
    			if (roverMap[y][x] == 0) {
    				switch (staticMap[y][x]) {
    				case 0:
    					// EMPTY
    					System.out.print("?");
    					break;
    				case WALL_CLASS_ID:
    					// WALL
    					System.out.print("#");
    					break;
    				}
    			} 
    			else {
					System.out.print("*");
    			}
    			System.out.print(" ");
    		}
    		System.out.println();
    	}
    	System.out.println("");
    }
    
    //saves current rover pos in map
    private void reportRoverPos() {
		self = observateSelf();
		roverMap[self.position.y][self.position.x] = 1;
		if (debugMode){
			log("roamer ist at " + " x=" + self.position.x + " y=" + self.position.y);
			printMap();
		}
	}
    
 //#endregion
            
//#region "Informations on Rover and Authors"
    
    @Override
	public String getAuthorName() {
        return "Roman and Max";
    }

    @Override
	public String getDescription() {
        return "A Rover";
    }
    
//#endregion
    
}