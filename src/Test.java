import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Player> pl = new ArrayList<>();
		GameConfig game = new GameConfigImp(1,20,10,500,10);
		OrganismsGameInterface og = new OrganismsGameImp();

		
		Player p = new RandomPlayer();
		p.register(game, 555);
		
		Player h = new PlayerHuman();
		h.register(game, 554);

		pl.add(p);
		pl.add(h);
		og.initialize(game, 0.001, 0.002, pl);
		og.playGame();
		
	}

}
