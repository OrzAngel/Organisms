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
	 * make a copy of the input PlayerRoundData
	 * @param prd - te prd to copy
	 */
	public PlayerRoundDataImp(PlayerRoundData prd) {
		this.id = prd.getPlayerId();
		this.energy = prd.getEnergy();
		this.count = prd.getCount();
	}
	
	/**
	 * update energy
	 * @param energy - the difference
	 */
	public PlayerRoundDataImp addEnergy(int energy) {
		this.energy += energy;
		return this;
	}

	/**
	 * update count
	 * @param count - the difference
	 */
	public PlayerRoundDataImp addCount(int count) {
		this.count += count;
		return this;
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
	
	@Override
	public String toString(){
		return String.format("id : %2d, energy : %4d, count: %3d\n", id,energy,count);
	}

}
