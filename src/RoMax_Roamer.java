/** A rover that looks before it moves. */
public class RoMax_Roamer extends Creature {
	
	//Variables
	
	
    @Override
	public void run() {
    	
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

    @Override
	public String getAuthorName() {
        return "Darwin SDK";
    }

    @Override
	public String getDescription() {
        return "A rover that looks before it moves.";
    }
}
