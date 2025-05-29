public class Commands {
    public static String create(String gameId, int size, long timeSec) {
        return String.format("create %s %d %d", gameId, size, timeSec);
    }

    public static String join(String gameId, String player) {
        return String.format("join %s %s", gameId, player);
    }

    public static String move(int x, int y) {
        return String.format("move %d %d", x, y);
    }
}
