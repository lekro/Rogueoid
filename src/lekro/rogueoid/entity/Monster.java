package lekro.rogueoid.entity;


public class Monster extends Entity {

	public Monster(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		int dir = getRand().nextInt(4);
		switch (dir) {
		case 0:
			setX(getX()+1);
			break;
		case 1:
			setX(getX()-1);
			break;
		case 2:
			setY(getY()+1);
			break;
		case 3:
			setY(getY()-1);
			break;
		}
	}

}
