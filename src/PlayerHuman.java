import java.util.Scanner;
/**
 * represent a human player, prompt the user to make a decision via command line
 * @author CJC
 *
 */
public class PlayerHuman implements Player {

	@SuppressWarnings("unused")
	private static GameConfig game;
	private static final String name = "Terran";
	private static Scanner input = new Scanner (System.in);

	private int key;

	@Override
	public void register(GameConfig game, int key) {
		// TODO Auto-generated method stub
		PlayerHuman.game = game;
		this.key = key;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name + key;
	}

	@Override
	public Move move(boolean[] food, int[] neighbors, int foodleft, int energyleft) {
		// TODO Auto-generated method stub
		display(food, neighbors, foodleft, energyleft);

		int d = -1;
		
		while (d < 0 || d > 5) {
			System.out.println("Select a move by entering the index before it");
			System.out.printf("0: Stay put\n1: move West\n2: move East\n3: move North\n4: move South\n5: Reproduce\n");
			try {
				d = input.nextInt();
			} catch (Exception e){
				input.nextLine();
			}
		}

		if (d == Constants.REPRODUCE) {
			
			while (d < 1 || d > 4) {
				System.out.println("Select a postion to put the child by entering the index before it");
				System.out.printf("1: West\n2: East\n3: North\n4: South\n");
				try {
					d = input.nextInt();
				} catch (Exception e){
					input.nextLine();
				}
			}
			
			return new Move(Constants.REPRODUCE, d, key);
		} else {

			return new Move(d);
		}
	}

	/**
	 * print the information from the OrganismGame on the screen 
	 * @param food a five-element array that indicates whether any food is in adjacent squares
	 * @param neighbors a five-element array that holds the details for any organism in an adjacent square. -1 is no organism present, any value >= 0 if organism present
	 * @param foodleft how much food is left on the current square
	 * @param energyleft the organism's remaining energy
	 */
	private void display(boolean[] food, int[] neighbors, int foodleft, int energyleft) {
		System.out.printf("===============================\n");
		System.out.printf("Food left: %2d Energy left: %4d\n",foodleft,energyleft);
		System.out.println("Below is the environment around:");
		System.out.printf("**|%1s%1s|**\n",food[Constants.NORTH] ? "F" : "", neighbors[Constants.NORTH] >= 0 ? "N" : "");
		System.out.printf("%1s%1s| @|%1s%1s\n", food[Constants.WEST] ? "F" : "", neighbors[Constants.WEST] >= 0 ? "N" : "",food[Constants.EAST] ? "F" : "", neighbors[Constants.EAST] >= 0 ? "N" : "");
		System.out.printf("**|%1s%1s|**\n",food[Constants.SOUTH] ? "F" : "", neighbors[Constants.SOUTH] >= 0 ? "N" : "");
		System.out.println("F means food exist, N means neighbor exist, * means don't know");
	}

}
