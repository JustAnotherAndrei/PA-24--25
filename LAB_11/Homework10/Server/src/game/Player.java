package game;

public class Player {
    private final String name;
    private final char symbol;
    private long timeLeftMillis;
    private long lastMoveTime;

    public Player(String name, char symbol, long timeLimitSeconds) {
        this.name = name;
        this.symbol = symbol;
        this.timeLeftMillis = timeLimitSeconds * 1000;
        this.lastMoveTime = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public synchronized boolean startTurn() {
        lastMoveTime = System.currentTimeMillis();
        return timeLeftMillis > 0;
    }

    public synchronized boolean endTurn() {
        long now = System.currentTimeMillis();
        timeLeftMillis -= (now - lastMoveTime);
        return timeLeftMillis > 0;
    }

    public long getTimeLeftMillis() {
        return timeLeftMillis;
    }
}
