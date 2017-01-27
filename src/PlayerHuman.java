import java.util.Scanner;

public class PlayerHuman implements Player {

	private GameConfig game;
	private int key;
	private static final String name = "Terran";
	private Scanner input = new Scanner (System.in);

	@Override
	public void register(GameConfig game, int key) {
		// TODO Auto-generated method stub
		this.game = game;
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
			System.out.println("Select a move by input the index before it");
			System.out.printf("0: Stay put\n1: move West\n2: move East\n3: move North\n4: move South\n5: Reproduce\n");
			try {
				d = input.nextInt();
			} catch (Exception e){
				
			}
		}

		switch(d) {
		case Constants.REPRODUCE:
			
			while (d < 1 || d > 4) {
				System.out.println("Select a postion to put the child by input the index before it");
				System.out.printf("1: West\n2: East\n3: North\n4: South\n");
				try {
					d = input.nextInt();
				} catch (Exception e){
					
				}
			}
			return new Move(Constants.REPRODUCE, d, key);

		default:

			return new Move(d);

		}

	}

	private void display(boolean[] food, int[] neighbors, int foodleft, int energyleft) {

		System.out.printf("Food left: %d Energy left: %d\n",foodleft,energyleft);
		System.out.println("Below is the environment around:");
		System.out.printf("**|%1s%1s|**\n",food[Constants.NORTH] ? "F" : "", neighbors[Constants.NORTH] >= 0 ? "N" : "");
		System.out.printf("%1s%1s| @|%1s%1s\n", food[Constants.WEST] ? "F" : "", neighbors[Constants.WEST] >= 0 ? "N" : "",food[Constants.EAST] ? "F" : "", neighbors[Constants.EAST] >= 0 ? "N" : "");
		System.out.printf("**|%1s%1s|**\n",food[Constants.SOUTH] ? "F" : "", neighbors[Constants.SOUTH] >= 0 ? "N" : "");
		System.out.println("F means food exist, N means neighbor exist, * means don't know");
	}

}
