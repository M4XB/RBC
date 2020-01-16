import java.awt.* ;

/** A rover that looks before it moves. */


public class RoMax_Roamer extends Creature {
	
	//Variables
	//Unexplored = 0
	//explored = 1
	//Wall = 2
	//
	public enum obstacles{
		unexplored, explored, wall
	}
	
	private obstacles[][] map; 
	
    @Override
	public void run() {
    	 Dimension mapSize = getMapDimensions();
    	map = new obstacles[mapSize.height][mapSize.width];
    	printOutMap(mapSize.height, mapSize.width);
    	
        while (true) {
            
            Observation obs = observe()[0];
            
            System.out.println(obs);
            
            
            //Schreibt in Map den Typ des Objektes auf das geschaut wird
            map[obs.position.y][obs.position.x] = getObstacleAsEnum(obs.classId);
            
            printOutMap(mapSize.height, mapSize.width);
            int d = distance(obs.position) - 1;
            // Move until the far edge
            for (int i = 0; i < d; ++i) {
            	//SChaut nach Links und Rechts und füllt Map
            	turnLeft();
            	obs = observe()[0];
            	map[obs.position.y][obs.position.x] = getObstacleAsEnum(obs.classId);
            	turnRight();
            	turnRight();
            	obs = observe()[0];
            	map[obs.position.y][obs.position.x] = getObstacleAsEnum(obs.classId);
            	turnLeft();
                if (! moveForward()) {
                    // Hit something unexpected!
                    attack();
                    break;
                }
                //Schreibt die abgelaufenen Stellen in die Map
                Observation self = observeSelf();
                map[self.position.y][self.position.x] = obstacles.explored;
            }
            
            if (isEnemy(obs)) {
                // Attack whatever we observed
                attack();
            }
            
            // Turn
            turnRight();
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
    
    private obstacles getObstacleAsEnum(int objectId) {
    	obstacles obstacleId = null;
    	switch(objectId) {
    		case 2:
    			obstacleId = obstacles.wall;
    		
    	}
    	return obstacleId;
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
