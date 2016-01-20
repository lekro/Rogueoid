package lekro.rogueoid;

import java.util.Random;

public abstract class RogueMath {

	public static int roll(int size, int limit) {
		Random random = new Random();
		int rand = 0;
		int rolls = limit / size;
		for (int i = 0; i < rolls; i++) {
			rand += 1 + random.nextInt(size);
		}
		return rand;
	}
	
}
