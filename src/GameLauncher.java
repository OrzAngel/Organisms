
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Tester class
 * @author CJC
 *
 */
public class GameLauncher {

	
	public static void main(String[] args){
		
		ArrayList<ArrayList<? extends PlayerRoundData>> results = new ArrayList<>();
		
		ArrayList<Player> players = new ArrayList<>();
		GameConfig game = new GameConfigImp(1,4,500,1000,50);

		Player p = new RandomPlayer();
		p.register(game, 555);
		
		Player d = new PlayerAI();
		d.register(game,552);
		
		Player h = new PlayerAI();
		h.register(game, 550);
		
		players.add(p);
		players.add(d);
		players.add(h);
		
		OrganismsGameInterface og = new OrganismsGameImp(false);
		og.initialize(game, 0.01, 0.02, players);
		
		while (og.playGame()){
			results.add(og.getResults());
		}
		
		File path = new File("OrganismGame.txt");
		try(PrintWriter out = new PrintWriter(path)) {
			
			for (ArrayList<? extends PlayerRoundData> result : results){
				
				for (PlayerRoundData prd : result) {
					out.printf("%3d,%6d,", prd.getCount(),prd.getEnergy());
				}
				out.println();

			}
			
			out.close();
			
		} catch (Exception e) {
			
		}
		

	}
	
}
