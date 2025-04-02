package com.imageapp.shell;

import com.imageapp.commands.*;
import com.imageapp.exceptions.CommandNotFoundException;
import com.imageapp.model.ImageCollection;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Shell {
    private Map<String, Command> commands = new HashMap<>();

    public Shell(ImageCollection collection) {
        commands.put("add", new AddCommand(collection));
        commands.put("remove", new RemoveCommand(collection));
        commands.put("update", new UpdateCommand(collection));
        commands.put("load", new LoadCommand(collection));
        commands.put("save", new SaveCommand(collection));
        commands.put("report", new ReportCommand(collection));
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Shell pentru gestionarea imaginilor. Tasteaza 'exit' pentru a ieși.");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.trim().equalsIgnoreCase("exit")) {
                System.out.println("Iesire din aplicatie. Sa traiti bine!");
                break;
            }
            String[] tokens = input.split("\\s+");
            if (tokens.length == 0) continue;
            String commandName = tokens[0];
            Command command = commands.get(commandName);
            if (command == null) {
                throw new CommandNotFoundException("Comanda inexistenta: " + commandName);
            }
            String[] args = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, args, 0, args.length);
            try {
                command.execute(args);
            } catch (Exception e) {
                System.err.println("Eroare la executarea comenzii: " + e.getMessage());
            }
        }
        scanner.close();
    }
}

