import java.util.ArrayList;
import java.util.Random;

/**
 * This class simulate this game, In each time cycle 
 * it will prompt organisms of different kind to make a move, and generate report for outside callers.
 * @author CJC
 *
 */
public class OrganismsGameImp implements OrganismsGameInterface {

	private static final int MAX_ROUND = 5000;
	private static final int INIT_ENERGY = 500;
	private static final int BOUND = 10;

	private GameConfig game;
	private boolean flag; // if organism.flag() == flag, then the player has not yet moved in this turn
	private int round; // track the number of rounds simulated
	private boolean display;
	
	private Cell[][] grid; // the game board 
	private ArrayList<PlayerRoundDataImp> results = new ArrayList<>();
	// here I decide to use the PlayerRoundDataImp of my own. However the getResult still return the generic one
	// its index is the same as the PlayerRoundData.id() on that index 
	
	public OrganismsGameImp() {
		this(false);
	}
	
	public OrganismsGameImp(boolean display) { 
		this.display = display;
	}
	
	@Override
	public void initialize(GameConfig game, double p, double q, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		this.game = game;

		// init & config the game board
		grid = new Cell[BOUND][BOUND];
		Cell.config(p, q, game.K());

		ArrayList<int[]> spawn = new ArrayList<>();
		for (int i = 0 ; i < BOUND ; i ++)
			for (int j = 0 ; j < BOUND ; j ++) {
				grid[i][j] = new Cell();
				spawn.add(new int[] {i,j});
			}

		Random rand = new Random();
		int[] coor;
		int id = 0;
		int initEnergy = Math.min(INIT_ENERGY, game.M());
		flag = true;
		round = 0;
		for (Player player : players) {
			coor = spawn.remove(rand.nextInt(spawn.size()));

			Organism organism = new Organism(id++, player).setEnergy(initEnergy).setFlag(flag);
			grid[coor[0]][coor[1]].moveIn(organism);

			results.add(new PlayerRoundDataImp(id).addCount(1).addEnergy(initEnergy));
		}
	}

	@Override
	public boolean playGame() {
		// TODO Auto-generated method stub
		//simulate one time cycle
		
		if (hasNextRound() == false) {
			return false;
		}
		
		Organism organism;
		PlayerRoundDataImp prdi;
		Move move;
		boolean[] food = new boolean[5];
		int[] neib = new int[5];
		int x,y;

		for (int i = 0 ; i < BOUND ; i++)
			for (int j = 0 ; j < BOUND ; j++) {
				grid[i][j].reproduceFood();
			}

		for (int i = 0 ; i < BOUND ; i++)
			for (int j = 0 ; j < BOUND ; j++) {

				organism = grid[i][j].getOrganism();
				if (organism == null || organism.flag() != flag) {
					continue;
				}
				
				prdi = results.get(organism.id());
				
				int energy = organism.energy();
				prdi.addEnergy(-energy);
				
				// eat if hungry
				if (energy <= game.M() - game.u() && grid[i][j].eatFood()) {
					energy += game.u();
				}

				// generate food[] & neighbor[]
				for (int dir : Constants.DIRECTIONS) {
					x = (j + Constants.CXTrans[dir] + BOUND) % BOUND;
					y = (i + Constants.CYTrans[dir] + BOUND) % BOUND;
					food[dir] = grid[y][x].getFood() > 0;
					neib[dir] = grid[y][x].getOrganism() == null ? -1 : 1;
				}
				// prompt organism to make a move
				move = organism.player().move(food, neib, grid[i][j].getFood(), energy);

				switch(move.type()){
				case Constants.REPRODUCE:

					x = (j + Constants.CXTrans[move.childpos()] + BOUND) % BOUND;
					y = (i + Constants.CYTrans[move.childpos()] + BOUND) % BOUND;

					if (grid[y][x].getOrganism() == null) {

						energy -= game.v();
						energy /= 2;
						if (energy > 0) {
							try {
								Organism child = new Organism(organism).setEnergy(energy).setFlag(!flag);
								child.player().register(game, move.key());
								grid[y][x].moveIn(child);

								prdi.addCount(1).addEnergy(energy);
							
							} catch (InstantiationException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								return false;
							}
						}

					} else {
						// if the target cell is occupied
						energy -= game.s();
					}

					break;

				case Constants.STAYPUT:

					energy -= game.s();
					break;

				default:

					x = (j + Constants.CXTrans[move.type()] + BOUND) % BOUND;
					y = (i + Constants.CYTrans[move.type()] + BOUND) % BOUND;

					if (grid[y][x].getOrganism() == null) {		

						energy -= game.v();
						if (energy > 0) {
							grid[y][x].moveIn(grid[i][j].moveOut());
						}
					} else {							
						// if the target cell is occupied
						energy -= game.s();
					}
				}

				// check if the organism survived in this time cycle
				if (energy <= 0) {
					grid[i][j].moveOut();
					prdi.addCount(-1);
				} else {
					organism.setEnergy(energy).setFlag(!flag);
					prdi.addEnergy(energy);
				}
			}

		flag = !flag;
		if (display) {
			System.out.println(round);
			print();
		}
//		
		// if MAX_ROUND is reached, no more next round
		return hasNextRound();
	}

	@Override
	public ArrayList<PlayerRoundData> getResults() {
		
		ArrayList<PlayerRoundData> res = new ArrayList<>();
		
		for (PlayerRoundDataImp prdi : results){
			res.add(new PlayerRoundDataImp(prdi));
		}
		
		return res;
	}
	
	/**
	 * check whether the game ends			
	 * @return true if the game is not end
	 */
	private boolean hasNextRound() {
		
		if (++round >= MAX_ROUND) {
			return false;
		}
		
		int sum = 0;
		for (PlayerRoundData prd : results) {
			sum += prd.getCount();
		}
		
		return sum > 0;
		
	}

	/**
	 * print the board to the console
	 */
	private void print() {
		Organism organism;		
		System.out.println("==================================================");
		for (int i = 0 ; i < BOUND ; i++) {
			for (int j = 0; j < BOUND ; j++) {
				organism = grid[i][j].getOrganism();
				System.out.printf("%2d %1s|",grid[i][j].getFood(), organism == null? "" : organism.player().name().charAt(0));
			}
			System.out.println();
		}
	}
}
