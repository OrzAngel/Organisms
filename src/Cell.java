import java.util.Random;
/**
 * represent a cell on the game board
 * responsible for tracking & reproducing food.
 * knows which organism is now living in this cell.
 * @author CJC
 *
 */
public class Cell {
	private static double p,q; 
	private static int K;
	private static Random rand;
	private int food;
	private Organism organism;

	/**
	 * constructor initialize a cell with 0 food and no one living in
	 *
	 */
	public Cell() {
		food = 0;
		organism = null;
	}


	/**
	 * 
	 * configure the game parameter related to food p , q & k, and a random generator
	 * all cells share the same parameter.
	 * @param p - possibility for food showing up 
	 * @param q - possibility for food reproducing 
	 * @param K - maximum food capacity
	 */
	public static void config(double p, double q, int K) {
		Cell.p = p;
		Cell.q = q;
		Cell.K = K;
		Cell.rand = new Random();
	}
	
	/**
	 * reproduce food if no organism occupies this cell
	 */
	public void reproduceFood() {
		if (organism != null || food >= K) {
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
	
	/**
	 * food is consumed by the organism living in this cell
	 * @return false if nothing to eat
	 */
	public boolean eatFood() {
		if (food > 0) {
			food--;
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return the number of food in this cell
	 */
	public int getFood(){
		return food;
	}
	
	/**
	 * 
	 * @return the organism living in this cell
	 */
	public Organism getOrganism(){
		return organism;
	}
	/**
	 * empty this cell
	 * @return the organism moving out
	 */
	public Organism moveOut(){
		Organism s = organism;
		organism = null;
		return s; 
	}
	
	/**
	 * assign this cell to one organism
	 * @param organism the organism moving in 
	 */
	public void moveIn(Organism organism) {
		this.organism = organism;
	}
}
