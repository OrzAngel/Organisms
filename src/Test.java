import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Player> pl = new ArrayList<>();
		GameConfig game = new GameConfigImp(1,4,500,1000,50);


		Player p = new RandomPlayer();
		p.register(game, 555);
		
		Player d = new RandomPlayer();
		d.register(game,552);

		Player h = new PlayerHuman();
		h.register(game, 554);

		Player s = new PlayerAI();
		s.register(game, 550);
		
		Player z = new PlayerAIv2();
		z.register(game, 553);

		pl.add(d);
		pl.add(s);
		pl.add(z);
		//		pl.add(h);

		OrganismsGameImp og;

		og = new OrganismsGameImp();
		og.initialize(game, 0.1, 0.2, pl);
		//		if (){
		if (!og.playGame()) {
			System.out.println("something unexpected happened");
		}
		ArrayList<PlayerRoundData> results = og.getResults();

		for (PlayerRoundData r : results) {
			System.out.printf("Brain id: %d, count left: %d, energy left: %d\n",r.getPlayerId(),r.getCount(),r.getEnergy());
		}
		
		((PlayerAIv2)z).print();


	}

}
