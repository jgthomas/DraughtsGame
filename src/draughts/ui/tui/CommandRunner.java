package draughts.ui.tui;

import java.util.HashMap;
import java.util.Map;

class CommandRunner {
    private final Map<String, Runnable> commandMap = new HashMap<>();

    CommandRunner(GameController gameController) {
        commandMap.put("help", this::help);
        commandMap.put("save", gameController::saveGame);
        commandMap.put("back", gameController::backOneMove);
        commandMap.put("forw", gameController::forwardOneMove);
        commandMap.put("play", gameController::aiResume);
        commandMap.put("rese", gameController::restartGame);
        commandMap.put("new", gameController::newGame);
        commandMap.put("clea", gameController::displayBoard);
        commandMap.put("quit", this::quit);
    }

    void execute(String commandCode) {
        String command = processCommand(commandCode);
        if (commandMap.containsKey(command)) {
            commandMap.get(command).run();
        } else {
            System.out.println("Command '" + commandCode + "' not recognised");
        }
    }

    private void help() {
        System.out.println();
        System.out.println("Game commands");
        System.out.println("#############");
        System.out.println("help    : print this message");
        System.out.println("save    : save game");
        System.out.println("back    : move back one move");
        System.out.println("forward : move forward one move");
        System.out.println("play    : make computer player resume");
        System.out.println("reset   : reset game to initial state");
        System.out.println("new     : start new game");
        System.out.println("clear   : clear all command text");
        System.out.println("quit    : exit the game");
        System.out.println();
    }

    private void quit() {
        System.exit(0);
    }

    private String processCommand(String commandCode) {
        String command = commandCode.toLowerCase();
        if (command.length() > 3) {
            command = command.substring(0,4);
        }
        return command;
    }
}
