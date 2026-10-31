package onlyzoom.modid.client;

public class ZoomHandler {
	private static final double MIN_FACTOR = 0.02;
	private static final double MAX_FACTOR = 0.90;
	private static final double SMOOTH_SPEED = 12.0;

	public static boolean zooming = false;

	private static double zoomFactor = 0.25;
	private static double current = 1.0;
	private static long lastNanos = System.nanoTime();

	public static void adjust(double scroll) {
		zoomFactor *= Math.pow(0.9, scroll);
		zoomFactor = Math.max(MIN_FACTOR, Math.min(MAX_FACTOR, zoomFactor));
	}

	public static double getMultiplier() {
		long now = System.nanoTime();
		double dt = Math.min((now - lastNanos) / 1_000_000_000.0, 0.1);
		lastNanos = now;

		double target = zooming ? zoomFactor : 1.0;
		current += (target - current) * (1.0 - Math.exp(-SMOOTH_SPEED * dt));

		if (Math.abs(target - current) < 0.0005) current = target;
		return current;
	}

	/** Valor atual do zoom (1.0 = sem zoom), sem avançar a animação. */
	public static double getCurrent() {
		return current;
	}
}