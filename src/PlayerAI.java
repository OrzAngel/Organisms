/**
 * Simplest AI player, reproduce when reach the max energy 
 * @author CJC
 *
 */
public class PlayerAI implements Player {
	
	private static GameConfig game = null;
	private static String name = "Zergling";
	// it is a really cooool idea here, that every organism shares the same overmind 
	
	private int key;
	
	
	
	@Override
	public void register(GameConfig game, int key) {
		// TODO Auto-generated method stub
		this.key = key;
		if (PlayerAI.game == null) {
			PlayerAI.game = game;
		}
		
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name + key;
	}

	@Override
	public Move move(boolean[] food, int[] neighbors, int foodleft, int energyleft) {
		// TODO Auto-generated method stub
		
		int pos = 0;
		for (int i = 1 ; i <= 4 ; i++) {
			if (food[i] && neighbors[i] == -1) {
				pos = i;
				break;
			}
		}
		
		if (foodleft > 0 && game.M() - game.u() < energyleft && pos != 0) {
			
			return new Move(Constants.REPRODUCE,pos,key);
		} else {
			return new Move(pos);
		}
			
	}

}
