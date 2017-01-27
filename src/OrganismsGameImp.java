import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class OrganismsGameImp implements OrganismsGameInterface {


	private static final int MAX_ROUND = 5000;
	private static final int INIT_ENERGY = 500;
	private static final int BOUND = 10;

	private GameConfig game;
	private Cell[][] grid;
	private HashMap<Player,Integer> organisms = new HashMap<>();
	private HashMap<Player,Boolean> movable = new HashMap<>();

	private boolean flag;

	@Override
	public void initialize(GameConfig game, double p, double q, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		this.game = game;

		grid = new Cell[BOUND][BOUND];
		Cell.config(p, q, game.K());

		ArrayList<int[]> spawn = new ArrayList<>();
		Random rand = new Random();

		for (int i = 0 ; i < BOUND ; i ++)
			for (int j = 0 ; j < BOUND ; j ++) {
				grid[i][j] = new Cell();
				spawn.add(new int[] {i,j});
			}

		int[] coor;
		int initEnergy = Math.min(INIT_ENERGY, game.M());
		for (Player player : players) {
			coor = spawn.remove(rand.nextInt(spawn.size()));
			grid[coor[0]][coor[1]].moveIn(player);
			organisms.put(player, initEnergy);
			movable.put(player,true);
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
		
		Scanner s = new Scanner(System.in);
		
		for (round = 0; round < MAX_ROUND; round++){
			
			if (organisms.keySet().size() == 0) {
				break;
			}
//			
//			print();
//			s.nextLine();
			
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

					int energy = organisms.get(player);
					if (energy <= game.M() - game.u() && grid[i][j].eatFood()) {
						energy += game.u();
					}

					for (int dir : Constants.DIRECTIONS) {
						x = (j + Constants.CXTrans[dir] + BOUND) % BOUND;
						y = (i + Constants.CYTrans[dir] + BOUND) % BOUND;
						food[dir] = grid[y][x].getFood() > 0;
						neib[dir] = grid[y][x].getOrganism() == null ? -1 : 1;
					}

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
									organisms.put(child, energy);
									movable.put(child, !flag);
								} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
										| InvocationTargetException | NoSuchMethodException | SecurityException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return false;
								}
							}

						} else {
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
							energy -= game.s();
						}

					}

					if (energy <= 0) {
						grid[i][j].moveOut();
						organisms.remove(player);
						movable.remove(player);
					} else {
						organisms.put(player, energy);
						movable.put(player, !flag);
					}

				} 
			flag = !flag;
			
		}
		System.out.println(round);
		return false;
	}

	@Override
	public ArrayList<PlayerRoundData> getResults() {
		// TODO Auto-generated method stub
		return null;
	}

	private void print() {
		
		System.out.println("==================================================");
		for (int i = 0 ; i < BOUND ; i++) {
			for (int j = 0; j < BOUND ; j++) {
				System.out.printf("%2d %1s|",grid[i][j].getFood(), grid[i][j].getOrganism() == null? "" : "*");
			}
			System.out.println();
		}
			
		
		
	}
	
}
