
public class Piece {
	
	String hero;
	String type;
	int gold;
	int ap;
	
	public Piece(String hero, String type, int gold, int ap) {
		this.hero = hero;
		this.type = type;
		this.gold = gold;
		this.ap = ap;
}

	public String getHero() {
		return hero;
	}

	public void setHero(String hero) {
		this.hero = hero;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getAp() {
		return ap;
	}

	public void setAp(int ap) {
		this.ap = ap;
	}
}
