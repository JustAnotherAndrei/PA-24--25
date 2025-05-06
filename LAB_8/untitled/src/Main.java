public class Main {
    public static void main(String[] args) throws Exception {
        Dictionary dictionary = new Dictionary("C:\\Users\\Andrei\\PA-24--25\\LAB_8\\untitled\\src\\words.txt");
        Bag bag = new Bag();
        Game game = new Game(dictionary, bag);

        Player p1 = new Player("Alice", dictionary, bag, game);
        Player p2 = new Player("Bob", dictionary, bag, game);
        game.addPlayer(p1);
        game.addPlayer(p2);

        Timekeeper timer = new Timekeeper(game, 30);
        timer.start();

        game.startGame();

        p1.join();
        p2.join();

        game.printResults();
    }
}
