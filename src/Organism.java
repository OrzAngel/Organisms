/**
 * 		this class represents one organism, tracks its playerID, energy, moving status and a "brain", namely a variable of type Player.class
 * @author CJC
 *
 */
public class Organism {
	
	// here I made all the status with package protection 
	private int id;
	private int energy;
	private boolean flag;
	private Player player;
	/**
	 * create a new organism
	 * @param id playerID
	 * @param player brain of this organism
	 */
	public Organism(int id, Player player) {
		this.id = id;
		this.player = player;
	}
	/**
	 * create a new organism from its parent
	 * @param parent the parent organism
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 */
	public Organism(Organism parent) throws InstantiationException, IllegalAccessException {
		this.id = parent.id;
		this.player = parent.player.getClass().newInstance();
	}
	
	/**
	 * set energy
	 * @param energy the new energy 
	 * @return this organism
	 */
	public Organism setEnergy(int energy) {
		this.energy = energy;
		return this;
	}
	
	/**
	 * set moving status
	 * @param flag moving status
	 * @return this organism
	 */
	public Organism setFlag(boolean flag) {
		this.flag = flag;
		return this;
	}

	/**
	 * get energy of this organism
	 * @return the energy
	 */
	public int energy(){
		return energy;
	}

	/**
	 * get the moving status of this organism
	 * @return the moving status
	 */
	public boolean flag(){
		return flag;
	}
	
	/**
	 * get the brain of this organism
	 * @return the brain of this organism
	 */
	public Player player() {
		return player;
	}
	
	/**
	 * get the player ID
	 * @return the player ID
	 */
	public int id(){
		return id;
	}
}
