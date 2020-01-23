import java.awt.* ;

/** A rover that looks before it moves. */


public class RoMax_Roamer extends Creature {
	
	private int[][] map; 
	
    @Override
	public void run() {
    	 Dimension mapSize = getMapDimensions();
    	map = new int[mapSize.height][mapSize.width];
    	//printOutMap(mapSize.height, mapSize.width);
    	easyLogic();
    	/*if (mapSize.height <15 && mapSize.width < 15) {
    		easyLogic();
    	}else {
    		complexAlgorithm();
    	}*/   
    }
    
    private void easyLogic() {
    	Observation obs = observe()[0];
    	if (obs.classId == WALL_CLASS_ID && distance(obs.position) == 1) {
    		turnLeft();	
    	}else {
    		moveForward(distance(obs.position)-1);
    	}
    	while(true) {
    		obs = observe()[0];
    		if (obs.classId == TREASURE_CLASS_ID) {
    			moveForward(distance(obs.position)-1);
    			attack();
    		}
    		if (moveForward()) {
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
        		}else {
    				turnLeft();
    			}
    		}else {
    			turnLeft();
    			obs = observe()[0];
    			if (obs.classId == WALL_CLASS_ID && distance(obs.position) != 1) {
    				moveForward(1);
    			}else {
    				turnLeft();
    				turnLeft();
    				if (obs.classId == WALL_CLASS_ID && distance(obs.position) != 1) {
        				moveForward(1);
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
                //Observation self = observeSelf();
            }
            
            if (isEnemy(obs)) {
                // Attack whatever we observed
                attack();
            }
        }
    }
    
    //Prints out up-to-date map in the console
    private void printOutMap(int height, int width) {
    	for (int i=0; i<height; i++) {
    		for (int y=0; y<width; y++) {
    			System.out.print(map[i][y] + " ");
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
