
public class PlayerRoundDataImp implements PlayerRoundData {
	
	private int id,energy,count;
	
	public PlayerRoundDataImp(int id,int energy) {
		this.id = id;
		this.energy = energy;
		count = 1;
	}

	public void setEnergy(int energy) {
		this.energy += energy;
	}

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
