package lekro.rogueoid.map.mask;

import java.util.LinkedList;
import java.util.Queue;

public class LayeredMapMask extends MapMask {

	private Queue<UniformMapMask> layers;
	private int limit;
	
	public LayeredMapMask(int limit, int width, int height) {
		super(width, height);
		this.limit = limit;
		layers = new LinkedList<UniformMapMask>();
	}

	@Override
	public boolean[][] toBooleanArray() {
		UniformMapMask mask = new UniformMapMask(getWidth(), getHeight());
		for (UniformMapMask u : layers) {
			mask.append(u.toBooleanArray());
		}
		return mask.toBooleanArray();
	}

	@Override
	public void clear() {
		layers = new LinkedList<UniformMapMask>();
	}

	@Override
	public void append(boolean[][] map) {
		UniformMapMask layer = new UniformMapMask(getWidth(), getHeight());
		layer.append(map);
		if (layers.size() >= getLimit()) {
			layers.poll();
		}
		layers.offer(layer);
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
