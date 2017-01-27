import java.util.Random;

public class Cell {
	private static double p,q;
	private static int K;
	private static Random rand;
	private int food;
	private Player player;

	public Cell() {
		food = 0;
		player = null;
	}

	public static void config(double p, double q, int K) {
		Cell.p = p;
		Cell.q = q;
		Cell.K = K;
		Cell.rand = new Random();
	}

	public void reproduceFood() {
		if (player != null || food >= K) {
			return;
		}

		if (food == 0) {
			if (rand.nextDouble() <= p) {
				food++;
				return;
			}
		} else {
			int k = food;
			for (int i = 1; i <= k ; i++) {
				if (rand.nextDouble() <= q) {
					food++;
					if (food >= K) {
						return;
					}
				}
			} 
		}
	}

	public boolean eatFood() {
		if (food > 0) {
			food--;
			return true;
		}
		return false;
	}

	public int getFood(){
		return food;
	}

	public Player getOrganism(){
		return player;
	}

	public Player moveOut(){
		Player p = player;
		player = null;
		return p; 
	}

	public void moveIn(Player player) {
		this.player = player;
	}
}
