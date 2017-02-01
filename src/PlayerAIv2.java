
public class PlayerAIv2 implements Player {
	
	private static final int BOUND = 10;
	
	private static GameConfig game = null;
	private static String name = "Zerg";
	// it is a really cooool idea here, that every organism shares the same overmind 
//	private static int round;
	private static int id;
	private static boolean atWar;
	private static int[][] foods; // -1 : unknown, >= 0 number of food, > game.k() exist. 
	private static int[][] organisms; //  -2: enemies; -1 : unknown; 0 : neutral; >= 1 comrade;
//	private static int[] energies; // energy left
//	private static int[] keys; // Circuit Queue, available keys left
//	private static int q1,q2; //start & end pointer for the keys
	
	//
	private int key;
	
	
	@Override
	public void register(GameConfig game, int key) {
		// TODO Auto-generated method stub
		this.key = key;
		if (PlayerAIv2.game == null) {
			
			PlayerAIv2.game = game;
			
			atWar = false;
			foods = new int[BOUND][BOUND];
			organisms = new int[BOUND][BOUND];
//			energies = new int[100];
//			keys = new int[100];
			
//			q1 = 0;
//			q2 = 0;
			id = 1;
			for (int i = 0 ; i < BOUND ; i++) 
				for (int j = 0 ; j < BOUND ; j++) {
					foods[i][j] = -1;
					organisms[i][j] = -1;
//					keys[q2++] = id++;
				}
			
//			q2 = 0;
			this.key = id; // the over-mind
			
			organisms[0][0] = this.key;
			
		}
		
	}

	@Override
	public String name() {
		return name + key;
	}

	@Override
	public Move move(boolean[] food, int[] neighbors, int foodleft, int energyleft) {
		
		int i,j,k;
		for (k = 0 ; k < BOUND * BOUND ; k++) {
			if (organisms[k/BOUND][k%BOUND] == key){
				break;
			}
		}
		
		i = k / BOUND;
		j = k % BOUND;
		
		foods[i][j] = foodleft;
		
		int x,y;
		int pos = 0;
		for (int dir = 1 ; dir <= 4 ; dir++) {
			x = (j + Constants.CXTrans[dir] + BOUND) % BOUND;
			y = (i + Constants.CYTrans[dir] + BOUND) % BOUND;
			
			if (food[dir]) {
				if (foods[y][x] <= 0) {
					foods[y][x] = game.K() + 1;
				}
			} else {
				if (!atWar && foods[y][x] > 0) {
					atWar = true;
				}
				foods[y][x] = 0;
			}
			
			if (neighbors[dir] == -1) {
				organisms[y][x] = 0;
			} else {
				if (organisms[y][x] <= 0 ) {
					organisms[y][x] = -2;
					atWar = true;
				}
			}
			
			if (food[dir] && neighbors[dir] == -1) {
				pos = dir;
			}	
		}
		
		if (foodleft > 0 && game.M() - game.u() < energyleft && pos != 0) {
			
			id++;
			x = (j + Constants.CXTrans[pos] + BOUND) % BOUND;
			y = (i + Constants.CYTrans[pos] + BOUND) % BOUND;
			organisms[y][x] = id;
			
			return new Move(Constants.REPRODUCE,pos, id);
		} else {
			
			if (pos != 0) {
				x = (j + Constants.CXTrans[pos] + BOUND) % BOUND;
				y = (i + Constants.CYTrans[pos] + BOUND) % BOUND;
				organisms[i][j] = 0;
				organisms[y][x] = key;
			}
			return new Move(pos);
		}
			
	}
	
	public void print() {
		System.out.println(atWar);
		System.out.println("==================================================");
		char c;
		for (int i = 0 ; i < BOUND ; i++) {
			for (int j = 0; j < BOUND ; j++) {
				if (organisms[i][j] > 0) {
					c = 'C';
				} else if (organisms[i][j] == -2) {
					c = 'E';
				} else {
					c = ' ';
				}
				System.out.printf("%2d %c|",foods[i][j],c);
			}
			System.out.println();	
		}
	}

}
