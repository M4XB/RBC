import java.awt.* ;

/** A rover that looks before it moves. */
public class RoMax_Roamer extends Creature {
	
	//Variables
		//Wall = 1
		//Explored = 0
	private int[][] map; 
	
    @Override
	public void run() {
    	
    	Dimension mapSize = getMapDimensions();
    	map = new int[mapSize.height][mapSize.width];
    	printOutMap(mapSize.height, mapSize.width);
    	
        while (true) {
            
            Observation obs = observe()[0];
            System.out.println(obs);
            
            int d = distance(obs.position) - 1;
            // Move until the far edge
            for (int i = 0; i < d; ++i) {
                if (! moveForward()) {
                    // Hit something unexpected!
                    attack();
                    break;
                }
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
