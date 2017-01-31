/**
 * My simple implementation of the PlayerRoundData interface
 * @author CJC
 *
 */
public class PlayerRoundDataImp implements PlayerRoundData {
	
	private int id,energy,count;
	
	/**
	 * constructor
	 * @param id the id assigned to this kind of player
	 */
	public PlayerRoundDataImp(int id) {
		this.id = id;
		energy = 0;
		count = 0;
	}

	/**
	 * update energy
	 * @param energy - the difference
	 */
	public void setEnergy(int energy) {
		this.energy += energy;
	}

	/**
	 * update count
	 * @param count - the difference
	 */
	public void setCount(int count) {
		this.count += count;
	}

	@Override
	public int getPlayerId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public int getEnergy() {
		// TODO Auto-generated method stub
		return energy;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}
	
	

}
