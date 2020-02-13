import java.awt.* ;
import java.io.Console;

public class RoMax_Roamer extends Creature {
	
	// set this to false to disable extended console logging & save resources
	private boolean debugMode = true;
	
	// array containing static obstacles
	private int[][] staticMap; 
	// array containing rover positions
	private int[][] roverMap; 
	
	int mapWidth;
	int mapHeight;

	// observe roamer position
    Observation self;
    Observation obs;
	
    // report roamer position
    public void reportRoverPosition() {
    	self = observeSelf();
    	roverMap[self.position.y][self.position.x] = 1;
    };
	// set this to false to disable extended console logging & save resources
	private boolean debugMode = false;
	
    @Override
	public void run() {
    	
	
		    // generates maps, sets global map size
			private void mapGen() {
				Dimension mapSize = getMapDimensions();
				mapWidth = mapSize.width;
				mapHeight = mapSize.height;
				staticMap = new int[mapHeight][mapWidth];
				roverMap = new int[mapHeight][mapWidth];
			}

		private void primateLogic() {
			
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

						            // after arriving at observed position, change direction
									System.out.println("turning right");
            
									// check for viable directions
									
										// don't move into a wall
									
										// if other options exists don't go in already explored regions
									turnRight();
								}
							}
						
		}
    	
       
    	mapGen();
    	printMap();
    	
    	easyLogic();
    	//stupidLogic();
    	//complexAlgorithm();
    	
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
    		staticMap[y][mapWidth-1] = 1;
    	}
    	for (int x=0; x< mapWidth; x++) {
    		staticMap[0][x] = 1;
    		staticMap[mapHeight-1][x] = 1;
    	}
    	
    }
    
    
// Start Region "Main logic functions"
    
    //Created by mistake when making the simple logic
    private void stupidLogic() {
    	Obstacle obstacle = new Obstacle();
    	obstacle = detection();
    	if (isWallInFrontOfYou(obstacle)) {
    		reportRoverPos();
    		turnLeft();	
    	}else {
    		
    		for (int d=0; d <obstacle.getDistance()-1; d++) {
    			reportRoverPos();
    			moveForward(1);
    		}
    		//moveForward(distance(obs.position)-1);
    	}
    	
    	while(true) {
    		obstacle = detection();
    		if (seeTreasure(obstacle)) {
    			moveForward(obstacle.getDistance()-1);
    			attack();
    		}
    		if (moveForward()) {
    			reportRoverPos();
    			turnLeft();
    			obstacle = detection();
    			if (isWallInFrontOfYou(obstacle)) {
    				turnRight();
    				obstacle = detection();
        			if (isWallInFrontOfYou(obstacle)) {
        				turnRight();
        			}
    			}else if (seeTreasure(obstacle)) {
    				
        		}else if (isWallAway(obstacle)) {
        			moveForward(obstacle.getDistance()-1);
        			reportRoverPos();
        		}else {
    				turnLeft();
    			}
    		}else {
    			reportRoverPos();
    			turnLeft();
    			obstacle = detection();
    			if (isWallAway(obstacle)) {
    				moveForward(1);
    				reportRoverPos();
    			}else {
    				reportRoverPos();
    				turnLeft();
    				turnLeft();
    				if (isWallAway(obstacle)) {
        				moveForward(1);
        				reportRoverPos();
        			}
    			}
    		}
    	}
    }
    
  //Left hand on the wall principle    
    private void easyLogic() {    	
    	Obstacle obstacle = new Obstacle();
    	obstacle = detection();
    	if (isWallInFrontOfYou(obstacle)) {
    		reportRoverPos();
    		turnRight();	
    	}
    	
    	//TODO: Find way out of running in circles
    	else {
    		
    		for (int d=0; d <obstacle.getDistance()-1; d++) {
    			reportRoverPos();
    			moveForward(1);
    		}
    		turnRight();
    		//moveForward(distance(obs.position)-1);
    	}
    	
    	//TODO: choosing between left or right hand on wall
    	
    	while(true) {
    		
    		obstacle = detection();
    		if (seeTreasure(obstacle)) {
    			moveForward(obstacle.getDistance()-1);
    			attack();
    		}
    		if (moveForward()) {
    			reportRoverPos();
    			turnLeft();
    			obstacle = detection();
    			if (isWallInFrontOfYou(obstacle)) {
    				turnRight();
    				obstacle = detection();
        			if (isWallInFrontOfYou(obstacle)) {
        				turnRight();
        			}
    			}else if (seeTreasure(obstacle)) {
    				
        		}else if (isWallAway(obstacle)) {
        			moveForward(1);
        			reportRoverPos();
        		}else {
    				turnLeft();
    			}
    		}else {
    			reportRoverPos();
    			turnLeft();
    			obstacle = detection();
    			if (isWallAway(obstacle)) {
    				moveForward(1);
    				reportRoverPos();
    			}else {
    				reportRoverPos();
    				turnLeft();
    				turnLeft();
    				if (isWallAway(obstacle)) {
        				moveForward(1);
        				reportRoverPos();
        			}
    			}
    		}
    	}
    }
    
    //unfinished
    private void complexAlgorithm() {
    	int wallCount = 0;
    	boolean moved = false;
    	while (true) {    
            Observation obs = observe()[0];
            //System.out.println(obs);
            
            //map[obs.position.y][obs.position.x] = obs.classId;
            //printMap(mapSize.height, mapSize.width);
            int d = distance(obs.position) - 1;
            if (d == 0) turnRight();
            // Move until the far edge
            for (int i = 0; i < d; ++i) {
            	int obsId = obs.classId;
            	if (!isEnemy(obs)) {
            	
            		moved = true;
            	    wallCount = 0;
            	    if(distance(obs.position) > 1){
            		moveForward();
            	    }else{
            		turnRight();
            	    }
            	}else {
            		switch (obsId) {
            		case WALL_CLASS_ID:
            			++wallCount;
            			moved = true;
            			if(distance(obs.position) > 1){
            				moveForward();
            			}else {
            			    turnRight();
            			}		
            			break;
            			
            		case TREASURE_CLASS_ID:	
            			attack();
            			break;
            		}
            		if(!moved) enemySight(obs);
            	}
                if (! moveForward()) {
                    // Hit something unexpected!
                    attack();
                }
            }
            
            // if enemy is observed
            if (isEnemy(obs)) {
                // Attack Enemy
            	System.out.println("Leeeeroy Jenkins");
                attack();
            }
        }
    }
    
// End Region "Main logic functions"
    
       
    
// Start Region "Printing stuff to the console"
    
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
    				case 1:
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
		self = observeSelf();
		roverMap[self.position.y][self.position.x] = 1;
		log("roamer ist at " + " x=" + self.position.x + "y=" + self.position.y);
	}
    
 // End Region "Printing stuff to the console"


// Start Region "Observing the area around the rover
   
    //Returns an object of the block the rover is looking at
    //including the id and distance to the object
    private Obstacle detection() {
    	Obstacle obstacle = new Obstacle();
    	obs = observe()[0];
    	obstacle.setDistance(distance(obs.position));
    	obstacle.setObstacleId(obs.classId);
    	printMap();
    	//TODO: change to classType
    	staticMap[obs.position.y][obs.position.x] = 1;
    	return obstacle;
    }
    
    //checks if rover is standing in front of a wall
    private boolean isWallInFrontOfYou(Obstacle obstacle) {
    	if (obstacle.getDistance() == 1 && obstacle.getObstacleId() == WALL_CLASS_ID) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //checks if a wall is more than 1 block away from rover
    private boolean isWallAway(Obstacle obstacle) {
    	if (obstacle.getDistance() > 1 ) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //checks if rover is seeing the treasure
    private boolean seeTreasure(Obstacle obstacle) {
    	if (obstacle.getObstacleId()  == TREASURE_CLASS_ID) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //checks if enemy is in range to attack
    private void enemySight(Observation enemy){	
    	int enemyDistance = distance(enemy.position);
		if(enemyDistance == 2){
	            delay();
	        }else if(enemyDistance == 1){
	            attack();
	        }else{
		    moveForward();
		}
    }
    
    // checks if rover was at this position
    private boolean isNextPositionKnown() {
    	self = observeSelf();
    	if (roverMap[self.position.y][self.position.x] == 1) {
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
    
// End Region "Observing the area around the rover
    
            
// Start Region "Informations on Rover and Authors"
    
    @Override
	public String getAuthorName() {
        return "Roman and Max";
    }

    @Override
	public String getDescription() {
        return "A Rover";
    }
    
// End Region "Informations on Rover and Authors"
    
}