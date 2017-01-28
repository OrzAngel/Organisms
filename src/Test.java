import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Player> pl = new ArrayList<>();
		GameConfig game = new GameConfigImp(1,2,500,1000,50);


		Player p = new RandomPlayer();
		p.register(game, 555);

		Player h = new PlayerHuman();
		h.register(game, 554);

		pl.add(p);
		//		pl.add(h);

		OrganismsGameImp og;
		int k = 0;
		do {
			og = new OrganismsGameImp();
			og.initialize(game, 0.1, 0.2, pl);
			og.playGame();
		} while (k++ < 20 && og.check());	

		if (!og.check()) {
			
			og.print();
			
			ArrayList<PlayerRoundData> results = og.getResults();

			for (PlayerRoundData r : results) {
				System.out.printf("Brain id: %d, count left: %d, energy left: %d\n",r.getPlayerId(),r.getCount(),r.getEnergy());
			}
		}

	}

}
