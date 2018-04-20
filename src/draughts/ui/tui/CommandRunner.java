package draughts.ui.tui;

import java.util.HashMap;
import java.util.Map;

class CommandRunner {
    private final Map<String, Runnable> commandMap = new HashMap<>();
    private final GameController gameController;


    CommandRunner(GameController gameController) {
        this.gameController = gameController;
        commandMap.put("help", this::help);
        commandMap.put("save", this::save);
        commandMap.put("back", this::back);
        commandMap.put("forw", this::forward);
        commandMap.put("play", this::play);
        commandMap.put("quit", this::quit);
    }

    void execute(String commandCode) {
        String command = commandCode.substring(0,4).toLowerCase();
        if (commandMap.containsKey(command)) {
            commandMap.get(command).run();
        } else {
            System.out.println("Command '" + commandCode + "' not recognised");
        }
    }

    private void help() {
        System.out.println("help    : print this message");
        System.out.println("save    : save game");
        System.out.println("back    : move back one move");
        System.out.println("forward : move forward one move");
        System.out.println("play    : make computer player resume");
        System.out.println("quit    : exit the game");
    }

    private void save() {
        gameController.offerToSaveGame();
    }

    private void back() {
        gameController.backOneMove();
        gameController.displayBoard();
    }

    private void forward() {
        gameController.forwardOneMove();
        gameController.displayBoard();
    }

    private void play() {
        gameController.aiResume();
        gameController.displayBoard();
    }

    private void quit() {
        System.exit(0);
    }
}
