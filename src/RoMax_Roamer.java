import java.awt.*;
import Djikstra.*;

public class RoMax_Roamer extends Creature {
	
	// set this to false to disable extended console logging & save resources
	private boolean debugMode = true;
	
	// contains all static entities
	private int[][] staticMap;
	// contains rover and enemy positions
	private int[][] mobileMap;

	private Algorithms algorithms = new Algorithms();
	
	int mapWidth;
	int mapHeight;

	int edgeCounter = 1;
	int vertexCounter = 1;
	
	Observation obs;
	Observation self;
	
    @Override
	public void run() {
		
    	mapGen();
    	printMap();
		//easyLogic();
		easyLogicMapUsing();
		
    }
    
    // generates maps, sets global map size
    private void mapGen() {
    	Dimension mapSize = getMapDimensions();
    	mapWidth = mapSize.width;
    	mapHeight = mapSize.height;
    	staticMap = new int[mapHeight][mapWidth];
    	mobileMap = new int[mapHeight][mapWidth];
    	
    	//fill static map at edge with wall
    	for (int y=0; y< mapHeight; y++) {
    		staticMap[y][0] = WALL_CLASS_ID;
    		staticMap[y][mapWidth-1] = WALL_CLASS_ID;
    	}
    	for (int x=0; x< mapWidth; x++) {
    		staticMap[0][x] = WALL_CLASS_ID;
    		staticMap[mapHeight-1][x] = WALL_CLASS_ID;
    	}
    	
    }
    
//#region " Main logic functions"    
	
  	//Left hand on the wall principle    
    private void easyLogic() { 
		//turns around and checks if the rover    	
		spinMyHeadRightRound();
		reportRoverPos();
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
	
	private void easyLogicMapUsing(){
		//turns around and checks if the rover    	
		spinMyHeadRightRound();
		reportRoverPos();
    	if (isObjectInFrontOfYou(WALL_CLASS_ID)) {
    		turnRight();
    	}else {
    		move(distance(obs.position)-1);
    		turnRight();
    	}
    	while(true) {
    		
    		//When the rover sees the treasure, its going to hit it
			seeTreasure();
			
    		if (isWallFront() == 1){ 						//direct in front
				if (isWallLeft() == 1){ 				//direct in left of the rover
					turnRight();
					
				} else if (isWallLeft() == 2){ 			//away from rover
					turnLeft();
				} else {								//unknown field before wall or no wall in map
					turnLeft();
					if (isWallFront() == 1){			//directin front
						turnRight();
					} else if (isWallFront() == 2){		//away from rover
						move(1);
					} else {							//unknown field before wall or no wall in map
						if (isObjectInFrontOfYou(WALL_CLASS_ID)) {
							turnRight();
						}else if (!isObjectInFrontOfYou(WALL_CLASS_ID)) {
							move(1);
						}else {
							turnLeft();
						}
					}
				}
			} else if (isWallFront() == 2){					//away from rover

				if (isWallLeft() == 1){						//direct in left of the rover
					move(1);
				} else if (isWallLeft() == 2){				//away from rover
					turnLeft();
				} else {									//unknown field before wall or no wall in map
					turnLeft();
						if (isWallFront() == 1){			//directin front
							turnRight();
						} else if (isWallFront() == 2){		//away from rover
							move(1);
						} else {							//unknown field before wall or no wall in map
							if (isObjectInFrontOfYou(WALL_CLASS_ID)) {
								turnRight();
							}else if (!isObjectInFrontOfYou(WALL_CLASS_ID)) {
								move(1);
							}else {
								turnLeft();
							}
						}
				}
			} else{											//unknown field before wall or no wall in map
				if (isObjectInFrontOfYou(WALL_CLASS_ID)){
					if (isWallLeft() == 1){ 				//direct in left of the rover
						turnRight();
					} else if (isWallLeft() == 2){ 			//away from rover
						turnLeft();
					} else {								//unknown field before wall or no wall in map
						turnLeft();
						if (isWallFront() == 1){			//directin front
							turnRight();
						} else if (isWallFront() == 2){		//away from rover
							move(1);
						} else {							//unknown field before wall or no wall in map
							if (isObjectInFrontOfYou(WALL_CLASS_ID)) {
								turnRight();
							}else if (!isObjectInFrontOfYou(WALL_CLASS_ID)) {
								move(1);
							}else {
								turnLeft();
							}
						}
					}
				}
			}
    	}
	}

//#endregion


//#region "Observing the area around the rover"
   
    //Returns an object of th class the rover is looking at and fills static map
    private void observeObject() {
    	obs = observe()[0];
    	staticMap[obs.position.y][obs.position.x] = obs.classId;
    }
	
	//Returns an object of the current position of the rover
    private void observateSelf() {
    	self = observeSelf();
    }
    
    //checks if rover is standing in front of the object
    private boolean isObjectInFrontOfYou(int classId) {
		observeObject();
		if (obs.classId == classId && distance(obs.position) == 1) {
    		return true;
    	} else {
    		return false;
		}
	}
	
    //checks if rover is seeing the treasure
    private void seeTreasure() {
		observeObject();
    	if (obs.classId  == TREASURE_CLASS_ID) {
    		moveForward(distance(obs.position)-1);
			attack();
    	}
	}
	
	//turns around and checks if the rover sees 
	private void spinMyHeadRightRound(){
		for (int turn = 0; turn < 4; turn ++){
			turnLeft();
			seeTreasure();
		}
	}

	//Finds fastest way to a passed Point usind Djikstra Algorithm
	private void pathFinder(Point destination){
		
	}
    
    //lets the rover move and updates the rover map
    private void move(int distance) {
    	for (int i = 0; i < distance; i++) {
    		moveForward(1);
        	reportRoverPos();
    	}
	}
	
	//lets Rover rotate in one cardinal direction
	private void rotateRover(Direction wantedDirection){
		observateSelf();
		Direction selfDirection = self.direction;
		//If rover is looking in the right Directions quit 
		if (selfDirection ==  wantedDirection) return;
		//Switch Case on direction of the rover
		//Per Case inner Switch case on wanted Direction for less turnings
		switch (selfDirection){
			case NORTH:
				switch (wantedDirection){
					case EAST:
						turnRight();
					case SOUTH:
						turnRight();
						turnRight();
					case WEST:
						turnLeft();
				}
			case EAST:
				switch (wantedDirection){
					case NORTH:
						turnLeft();
					case SOUTH:
						turnRight();
					case WEST:
						turnLeft();
						turnLeft();
				}
			case SOUTH:
				switch (wantedDirection){
					case NORTH:
						turnLeft();
						turnLeft();
					case EAST:
						turnLeft();
					case WEST:
						turnRight();
				}
			case WEST:
				switch (wantedDirection){
					case NORTH:
						turnRight();
					case EAST:
						turnLeft();
					case SOUTH:
						turnRight();
						turnRight();
				}
		}
	}
	
	/**
	 * Checks with the static map, if a wall is left of the rover
	 * @return 1 if Wall
	 * @return 0 if unknown 
	 * @return everything else
	 */
	private int isWallLeft(){
		observateSelf();
		int res = 0;
		switch (self.direction){
			case NORTH:
				res = wallInDirection(Direction.WEST);
			case EAST:
				res = wallInDirection(Direction.NORTH);
			case SOUTH:
				res = wallInDirection(Direction.EAST);
			case WEST:
				res = wallInDirection(Direction.SOUTH);
			default:
			//Never happens
		}
		return res;
	}

	/**
	 * Checks with the static map, if a wall is left of the rover
	 * @return 1 if Wall
	 * @return 0 if unknown 
	 * @return everything else
	 */
	private int isWallFront(){
		observateSelf();
		return wallInDirection(self.direction);
	}

	/**
	 * Determines if theres a Wall in the given direction from the rover with no unkown spaces in between in the map
	 * @param direction Direction
	 * @return 0 if there is no wall, or unkown spaces in between, 1 if the wall is next to the rover,  2 if there is a wall, but with space
	 */
	private int wallInDirection(Direction direction){
		observateSelf();
		int res = 0;
		switch (direction){
			case NORTH:
				//If the first first field is unkown, return 0
				if(staticMap[self.position.y-1][self.position.x] == 0){
					res = 0;
				//If the first first field is a wall, return 1
				} else if(staticMap[self.position.y-1][self.position.x] == WALL_CLASS_ID){
					res = 1;
				}
				//Loop over all fields in the north of the rover
				//If an unkown field is found before a wall return 0
				//If a wall is found before an unkown field return 2 
				for(int tmpY = self.position.y-1; tmpY >=1; tmpY--){
					if (staticMap[tmpY][self.position.x] == 0){
						res = 0;
					} else if (staticMap[tmpY][self.position.x] == WALL_CLASS_ID ){
						res = 2;
					}
				}
			case EAST:
				//If the first first field is unkown, return 0
				if(staticMap[self.position.y][self.position.x+1] == 0){
					res = 0;
				//If the first first field is a wall, return 1
				} else if(staticMap[self.position.y][self.position.x+1] == WALL_CLASS_ID){
					res = 1;
				}
				//Loop over all fields in the east of the rover
				//If an unkown field is found before a wall return 0
				//If a wall is found before an unkown field return 2 
				for(int tmpX = self.position.x+1; tmpX < mapWidth; tmpX++){
					if (staticMap[self.position.y][tmpX] == 0){
						res = 0;
					} else if (staticMap[self.position.y][tmpX] == WALL_CLASS_ID ){
						res = 2;
					}
				}
			case SOUTH:
				//If the first first field is unkown, return 0
				if(staticMap[self.position.y+1][self.position.x] == 0){
					res = 0;
				//If the first first field is a wall, return 1
				} else if(staticMap[self.position.y+1][self.position.x] == WALL_CLASS_ID){
					res = 1;
				}
				//Loop over all fields in the south of the rover
				//If an unkown field is found before a wall return 0
				//If a wall is found before an unkown field return 2 
				for(int tmpY = self.position.y+1; tmpY <mapHeight; tmpY++){
					if (staticMap[tmpY][self.position.x] == 0){
						res = 0;
					} else if (staticMap[tmpY][self.position.x] == WALL_CLASS_ID ){
						res = 2;
					}
				}
			case WEST:
				//If the first first field is unkown, return 0
				if(staticMap[self.position.y][self.position.x-1] == 0){
					res = 0;
				//If the first first field is a wall, return 1
				} else if(staticMap[self.position.y][self.position.x-1] == WALL_CLASS_ID){
					res = 1;
				}
				//Loop over all fields in the west of the rover
				//If an unkown field is found before a wall return 0
				//If a wall is found before an unkown field return 2 
				for(int tmpX = self.position.x-1; tmpX > 0; tmpX--){
					if (staticMap[self.position.y][tmpX] == 0){
						res = 0;
					} else if (staticMap[self.position.y][tmpX] == WALL_CLASS_ID ){
						res = 2;
					}
				}
			default:
				//Wird nie errreicht
		}
		return res;
	}

    //checks if enemy is in range to attack
    private void enemyInSight(){	
		observeObject();
    	int enemyDistance = distance(obs.position);
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
    	if (mobileMap[position.y][position.x] == 1) {
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
		if (!debugMode) return;
    	for (int y=0; y<mapHeight; y++) {
    		for (int x=0; x<mapWidth; x++) {
    			
    			// if position previously unvisited by roamer
    			if (mobileMap[y][x] == 0) {

    				switch (staticMap[y][x]) {
    				case 0: // UNINITIALIZED_CLASS_ID
    					System.out.print("?");
    					break;
    				case 1: // EMPTY_CLASS_ID 
    					System.out.print(" ");
    					break;
    				case WALL_CLASS_ID:
    					System.out.print("X");
    					break;
    				case TREASURE_CLASS_ID:  
    					System.out.print("S");
    					break;
    				default: // UNKNOWN ENTITY
    					System.out.print("�");
    					break;
    				}
    			} 
    			else {
    				// EMPTY POSITION, PREVIOUSLY VISITED BY ROAMER
					System.out.print("*");
    			}
    			// spaces between map tiles
    			System.out.print(" ");
    		} // new line in map
    		System.out.println();
    	} // empty line after map
		System.out.println("");
    }
    
    //saves current rover position in map
    private void reportRoverPos() {
		observateSelf();
		mobileMap[self.position.y][self.position.x] = 1;
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