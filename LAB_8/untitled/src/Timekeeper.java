public class Timekeeper extends Thread {
    private final Game game;
    private final long limit;

    public Timekeeper(Game game, long seconds) {
        this.game = game;
        this.limit = seconds;
        setDaemon(true);
    }

    public void run() {
        long start = System.currentTimeMillis();
        while (true) {
            long elapsed = (System.currentTimeMillis() - start) / 1000;
            if (elapsed >= limit) {
                System.out.println("\nTime limit reached!");
                game.stopGame();
                return;
            }
            System.out.println("Elapsed: " + elapsed + "s");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                return;
            }
        }
    }
}
