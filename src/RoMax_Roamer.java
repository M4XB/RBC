import java.awt.* ;
import java.io.Console;

/** A rover that looks before it moves. */


public class RoMax_Roamer extends Creature {
	
	// set this to false to disable extended console logging & save resources
	private boolean debugMode = true;
	
	private int[][] staticMap;
	private int[][] roverMap;
	
	int mapWidth;
	int mapHeight;
	
	Observation obs;
	Observation self;
	
    @Override
	public void run() {
    	//console.clear();
    	mapGen();
    	printOutMap();
    	
    	easyLogic();
    	
    	/*if (mapSize.height <15 && mapSize.width < 15) {
    		easyLogic();
    	}else {
    		complexAlgorithm();
    	}*/   
    }
    
    // debug logger
    public void log(String msg) {
    	if (debugMode) {
    	System.out.println(msg);
    	}
    }
    
    // generates maps, sets global map size
    private void mapGen() {
    	Dimension mapSize = getMapDimensions();
    	mapWidth = mapSize.width;
    	mapHeight = mapSize.height;
    	staticMap = new int[mapHeight][mapWidth];
    	roverMap = new int[mapHeight][mapWidth];
    }
    
    // saves current rover pos in map
    private void reportRoverPos() {
		self = observeSelf();
		roverMap[self.position.y][self.position.x] = 1;
		log("roamer ist at " + " x=" + self.position.x + "y=" + self.position.y);
	}
    
    //Einfache Logik
    //Nach dem Printip linke Hand an die Wand
    //Wenn der Schatz gesehen wird, wird auf diesen direkt gelaufen
    private void easyLogic() {
    	obs = observe()[0];
    	if (obs.classId == WALL_CLASS_ID && distance(obs.position) == 1) {
    		reportRoverPos();
    		turnLeft();	
    	}else {
    		
    		for (int d=0; d <distance(obs.position)-1; d++) {
    			reportRoverPos();
    			moveForward(1);
    		}
    		//moveForward(distance(obs.position)-1);
    	}
    	
    	while(true) {
    		obs = observe()[0];
    		if (obs.classId == TREASURE_CLASS_ID) {
    			moveForward(distance(obs.position)-1);
    			attack();
    		}
    		if (moveForward()) {
    			reportRoverPos();
    			turnLeft();
    			obs = observe()[0];
    			if (obs.classId == WALL_CLASS_ID && distance(obs.position) == 1) {
    				turnRight();
        			obs = observe()[0];
        			if (obs.classId == WALL_CLASS_ID && distance(obs.position) == 1) {
        				turnRight();
        			}
    			}else if (obs.classId == TREASURE_CLASS_ID && distance(obs.position) > 1) {
    				
        		}else if (obs.classId == WALL_CLASS_ID && distance(obs.position) > 1) {
        			moveForward(1);
        			reportRoverPos();
        		}else {
    				turnLeft();
    			}
    		}else {
    			reportRoverPos();
    			turnLeft();
    			obs = observe()[0];
    			if (obs.classId == WALL_CLASS_ID && distance(obs.position) != 1) {
    				moveForward(1);
    				reportRoverPos();
    			}else {
    				reportRoverPos();
    				turnLeft();
    				turnLeft();
    				if (obs.classId == WALL_CLASS_ID && distance(obs.position) != 1) {
        				moveForward(1);
        				reportRoverPos();
        			}
    			}
    		}
    	}
    }
    
    
    private void complexAlgorithm() {
    	int wallCount = 0;
    	boolean moved = false;
    	while (true) {    
            Observation obs = observe()[0];
            //System.out.println(obs);
            
            //Schreibt in Map den Typ des Objektes auf das geschaut wird
            //map[obs.position.y][obs.position.x] = obs.classId;
            //printOutMap(mapSize.height, mapSize.width);
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
                    break;
                }
                //Schreibt die abgelaufenen Stellen in die Map
                Observation self = observeSelf();
            }
            
            if (isEnemy(obs)) {
                // Attack whatever we observed
                attack();
            }
        }
    }
    
    //Prints out up-to-date map in the console
    private void printOutMap() {
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
    					System.out.print("?");
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
    }
    
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
    
        
    @Override
	public String getAuthorName() {
        return "Darwin SDK";
    }

    @Override
	public String getDescription() {
        return "A rover that looks before it moves.";
    }
}
