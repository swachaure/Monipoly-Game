import java.io.IOException;
import java.util.Scanner;

public class Monopoly {

    private static final Board board = Board.getInstance();
    private static final Rolls rolls = Rolls.getInstance();

    private static Integer balanceOfPlayer1;
    private static Integer balanceOfPlayer2;
    private static Integer balanceOfPlayer3;
    private static Integer balanceOfPlayer4;

    public static void main(String[] args) throws IOException {
        // initialize and load the board
        init();

        boolean stopFlag = false;
        boolean welcomePrint = true;
        do {
            if (welcomePrint) {
                Utility.printHash();
                System.out.println();
                System.out.println();
                System.out.println("\t\t\t\t\tWelcome to monopoly game");
                System.out.printf("\t\t Four player are loaded ");
                Board.getInstance().printAllPlayersName();
                System.out.println();
                System.out.println("\t\t\t Board loaded with options like as below");
                System.out.println();
                Board.getInstance().printAllProperties();
                Utility.printHash();
                System.out.println("Your options are as below");
                System.out.println("Simulate Game (Press 1 for start game)");
            }

            System.out.println("Exit from Game (Press 2 for exit game)");
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            switch (option) {
                case 1: {
                    welcomePrint = false;
                    System.out.println("You've Started the monopoly game");
                    start();
                }
                break;
                case 2: {
                    welcomePrint = false;
                    stopFlag = true;
                    System.exit(0);
                }
                break;
            }
        } while (!stopFlag);

    }

    private static void init() throws IOException {
        // add properties cards
        board.addPropertiesToBoard();

        Player peter = new Player("Peter");
        Player billy = new Player("Billy");
        Player charlotte = new Player("Charlotte");
        Player sweedal = new Player("Sweedal");

        //add players start
        board.addPlayer(peter);
        board.addPlayer(billy);
        board.addPlayer(charlotte);
        board.addPlayer(sweedal);
        //add players end

        // load all rolls
        Rolls rolls = Rolls.getInstance();
        rolls.loadRolls();

    }

    private static void start() {
        boolean init = true;
        Utility.printDash();
        for (int i = 1; i <= 48; i = i + 4) {
            int player1 = i;
            int player2 = player1 + 1;
            int player3 = player2 + 1;
            int player4 = player3 + 1;

            int player1Dice1 = rolls.getAllRollsOfDice1().get(player1);
            int player1Dice2 = rolls.getAllRollsOfDice2().get(player1);

            int player2Dice1 = rolls.getAllRollsOfDice1().get(player2);
            int player2Dice2 = rolls.getAllRollsOfDice2().get(player2);

            int player3Dice1 = rolls.getAllRollsOfDice1().get(player3);
            int player3Dice2 = rolls.getAllRollsOfDice2().get(player3);

            int player4Dice1 = rolls.getAllRollsOfDice1().get(player4);
            int player4Dice2 = rolls.getAllRollsOfDice2().get(player4);

            int player1Total = (init) ? player1Dice1 + player1Dice2 + 1 : player1Dice1 + player1Dice2;
            int player2Total = (init) ? (player2Dice1 + player2Dice2) + 1 : (player2Dice1 + player2Dice2);
            int player3Total = (init) ? (player3Dice1 + player3Dice2) + 1 : (player3Dice1 + player3Dice2);
            int player4Total = (init) ? (player4Dice1 + player4Dice2) + 1 : (player4Dice1 + player4Dice2);
            init = false;

            board.setCurrentPosOfPlayer1(player1Total);
            board.setCurrentPosOfPlayer2(player2Total);
            board.setCurrentPosOfPlayer3(player3Total);
            board.setCurrentPosOfPlayer4(player4Total);

            String currentPositionOfPlayer1 = board.getPropertiesMap().get(board.getCurrentPosOfPlayer1()).getName();
            String currentPositionOfPlayer2 = board.getPropertiesMap().get(board.getCurrentPosOfPlayer2()).getName();
            String currentPositionOfPlayer3 = board.getPropertiesMap().get(board.getCurrentPosOfPlayer3()).getName();
            String currentPositionOfPlayer4 = board.getPropertiesMap().get(board.getCurrentPosOfPlayer4()).getName();

            balanceOfPlayer1 = board.getBalance("Peter");
            balanceOfPlayer2 = board.getBalance("Billy");
            balanceOfPlayer3 = board.getBalance("Charlotte");
            balanceOfPlayer4 = board.getBalance("Sweedal");

            System.out.println("Player 1's Turn ( " + player1 + " ) Dice 1 ( " + player1Dice1 + " ) " + "Dice 2 ( " + player1Dice2 + " )" + " Total ( " + player1Total + " )" + " Current Position ( " + currentPositionOfPlayer1 + " )" + " Peter's Balance ( " + balanceOfPlayer1 + " )");
            System.out.println("Player 2's Turn ( " + player2 + " ) Dice 1 ( " + player2Dice1 + " ) " + "Dice 2 ( " + player2Dice2 + " )" + " Total ( " + player2Total + " )" + " Current Position ( " + currentPositionOfPlayer2 + " )" + " Billy's Balance ( " + balanceOfPlayer2 + " )");
            System.out.println("Player 3's Turn ( " + player3 + " ) Dice 1 ( " + player3Dice1 + " ) " + "Dice 2 ( " + player3Dice2 + " )" + " Total ( " + player3Total + " )" + " Current Position ( " + currentPositionOfPlayer3 + " )" + " Charlotte's Balance ( " + balanceOfPlayer3 + " )");
            System.out.println("Player 4's Turn ( " + player4 + " ) Dice 1 ( " + player4Dice1 + " ) " + "Dice 2 ( " + player4Dice2 + " )" + " Total ( " + player4Total + " )" + " Current Position ( " + currentPositionOfPlayer4 + " )" + " Sweedal's Balance ( " + balanceOfPlayer4 + " )");

            if (balanceOfPlayer1 <= 0 || balanceOfPlayer2 <= 0 || balanceOfPlayer3 <= 0 || balanceOfPlayer4 <= 0) {
                printFooter();
                System.exit(0);
            }
            Utility.printDash();
        }
    }

    private static void printFooter() {
        Utility.printHash();
        Utility.printStar();
        System.out.println();
        System.out.println("\t\t\t\t\t\tGame Completed");
        System.out.println("\t\t\t\t\t\t--------------");
        System.out.println();

        System.out.println("\t\t\t* * *  1. Peter's Balance ( " + balanceOfPlayer1 + " )\t \t * * * ");
        System.out.println("\t\t\t* * *  2. Billy's Balance ( " + balanceOfPlayer2 + " ) \t * * * ");
        System.out.println("\t\t\t* * *  3. Charlotte's Balance ( " + balanceOfPlayer3 + " ) * * * ");
        System.out.println("\t\t\t* * *  4. Sweedal's Balance ( " + balanceOfPlayer4 + " )\t * * * ");
        System.out.println();

        board.getPlayers().get("Peter").printAllOwnedProperties();
        board.getPlayers().get("Billy").printAllOwnedProperties();
        board.getPlayers().get("Charlotte").printAllOwnedProperties();
        board.getPlayers().get("Sweedal").printAllOwnedProperties();
        System.out.println();

        if (balanceOfPlayer1 <= 0) {
            System.out.println("***************************** Peter is bankrupt *****************************");
        }
        if (balanceOfPlayer2 <= 0) {
            System.out.println("***************************** Billy is bankrupt *****************************");
        }
        if (balanceOfPlayer3 <= 0) {
            System.out.println("***************************** Charlotte is bankrupt *****************************");
        }
        if (balanceOfPlayer4 <= 0) {
            System.out.println("***************************** Sweedal is bankrupt *****************************");
        }

        int winner = balanceOfPlayer1;
        String winnerPlayer = "Peter";
        if (balanceOfPlayer2 > winner) {
            winnerPlayer = "Billy";
            winner = balanceOfPlayer2;
        }
        if (balanceOfPlayer3 > winner) {
            winnerPlayer = "Charlotte";
            winner = balanceOfPlayer3;
        }
        if (balanceOfPlayer4 > winner) {
            winnerPlayer = "Billy";
            winner = balanceOfPlayer4;
        }

        System.out.println(" | *** " + winnerPlayer + " *** | With Balance ( " + winner + " ) Is Game Winner");

        System.out.println();
        Utility.printStar();
        Utility.printHash();
    }
}
