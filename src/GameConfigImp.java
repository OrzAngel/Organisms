
public class GameConfigImp implements GameConfig {
	
	int s,v,u,M,K;
	
	
	/**
	 * @param s
	 * @param v
	 * @param u
	 * @param m
	 * @param k
	 */
	public GameConfigImp(int s, int v, int u, int m, int k) {
		this.s = s;
		this.v = v;
		this.u = u;
		M = m;
		K = k;
	}
	
	public GameConfigImp() {
		s = 1;
		v = 11;
		u = 255;
		M = 550;
		K = 30;
	}

	@Override
	public int s() {
		// TODO Auto-generated method stub
		return s;
	}

	@Override
	public int v() {
		// TODO Auto-generated method stub
		return v;
	}

	@Override
	public int u() {
		// TODO Auto-generated method stub
		return u;
	}

	@Override
	public int M() {
		// TODO Auto-generated method stub
		return M;
	}

	@Override
	public int K() {
		// TODO Auto-generated method stub
		return K;
	}

}
