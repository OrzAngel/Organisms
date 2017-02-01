import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class OrganismsGameImp implements OrganismsGameInterface {


	private static final int MAX_ROUND = 5000;
	private static final int INIT_ENERGY = 500;
	private static final int BOUND = 10;

	private GameConfig game;
	private Cell[][] grid; // the game board 
	private HashMap<Player,Integer> energies = new HashMap<>(); // map organism to its energy
	private HashMap<Player,Boolean> movable = new HashMap<>();   // map organism to its moving status
	private ArrayList<Class<? extends Player>> brains = new ArrayList<>(); // a list of different kind of brains
	private ArrayList<PlayerRoundData> results = new ArrayList<>(); // data on index i is related to the kind of brain that on the same index in the brains list
	
	private boolean flag; // if movable.get(player) == flag, then the player has not moved in this turn

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
		for (Player player : players) {
			coor = spawn.remove(rand.nextInt(spawn.size()));
			grid[coor[0]][coor[1]].moveIn(player);
			energies.put(player, initEnergy);
			movable.put(player,true);
			
			Class<? extends Player> brainKind = player.getClass();
			if (!brains.contains(brainKind)) {
				brains.add(brainKind);
				results.add(new PlayerRoundDataImp(id++));
			}

		}

		flag = true;
	}

	@Override
	public boolean playGame() {
		// TODO Auto-generated method stub

		Player player;
		Move move;
		boolean[] food = new boolean[5];
		int[] neib = new int[5];
		int x,y;

		int round;
				
		for (round = 0; round < MAX_ROUND; round++){
			
			if (energies.keySet().size() == 0) {
				// everything extinct
				break;
			}
						
			for (int i = 0 ; i < BOUND ; i++)
				for (int j = 0 ; j < BOUND ; j++) {
					grid[i][j].reproduceFood();
				}

			for (int i = 0 ; i < BOUND ; i++)
				for (int j = 0 ; j < BOUND ; j++) {
					
					player = grid[i][j].getOrganism();
					if (player == null || movable.get(player) != flag) {
						continue;
					}
										
					int energy = energies.get(player);
					
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
					move = player.move(food, neib, grid[i][j].getFood(), energy);

					switch(move.type()){
					case Constants.REPRODUCE:

						x = (j + Constants.CXTrans[move.childpos()] + BOUND) % BOUND;
						y = (i + Constants.CYTrans[move.childpos()] + BOUND) % BOUND;

						if (grid[y][x].getOrganism() == null) {

							energy -= game.v();
							energy /= 2;

							if (energy > 0) {
								try {
									Player child = player.getClass().getConstructor().newInstance();
									child.register(game, move.key());
									grid[y][x].moveIn(child);
	
									energies.put(child, energy);
									movable.put(child, !flag);
								} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
										| InvocationTargetException | NoSuchMethodException | SecurityException e) {
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
						energies.remove(player);
						movable.remove(player);
					} else {
						energies.put(player, energy);
						movable.put(player, !flag);
					}
				} 
			
//			print();
			flag = !flag;
		}
		System.out.println(round);
		print();

		return true;
	}

	@Override
	public ArrayList<PlayerRoundData> getResults() {
		// TODO Auto-generated method stub
		Player player;
		PlayerRoundDataImp prd;
		
		for (int i = 0 ; i < BOUND; i++) 
			for (int j = 0 ; j < BOUND; j++) {
				player = grid[i][j].getOrganism();
				if (player == null) {
					continue;
				}
				prd = (PlayerRoundDataImp) results.get(brains.indexOf(player.getClass()));
				prd.setCount(1);
				prd.setEnergy(energies.get(player));
			}
		
		return results;
	}

	private void print() {
		Player player;		
		System.out.println("==================================================");
		for (int i = 0 ; i < BOUND ; i++) {
			for (int j = 0; j < BOUND ; j++) {
				player = grid[i][j].getOrganism();
				System.out.printf("%2d %1s|",grid[i][j].getFood(), player == null? "" : player.name().charAt(0));
			}
			System.out.println();
		}
	}
}
