import java.io.IOException;
import java.util.Scanner;

public class Monopoly {

	private static Board board;
	private static Rolls rolls;

	private static Integer balanceOfPlayer1;
	private static Integer balanceOfPlayer2;
	private static Integer balanceOfPlayer3;
	private static Integer balanceOfPlayer4;

	public static void main(String[] args) throws IOException {
		// initialize and load the board

		boolean stopFlag = false;
		boolean welcomePrint = true;
		do {
//			board = Board.getInstance();
			if (welcomePrint) {
				Utility.printHash();
				System.out.println();
				System.out.println("\t\t\t\t\tWelcome to monopoly game");
				System.out.println();
				Utility.printHash();
				System.out.println();
			}
			System.out.println("Your options are as below");
			System.out.println("Simulate Game (Press 1 for simulate game of rolls1)");
			System.out.println("Simulate Game (Press 2 for simulate game of rolls2)");
			System.out.println("Exit from Game (Press 3 for exit game)");
			Scanner sc = new Scanner(System.in);
			String option = sc.next();
			switch (option) {
				case "1": {
					board = new Board();
					rolls = new Rolls();

					init();
					Utility.printStar();
					System.out.println("You've Started simulating the monopoly game");
					System.out.printf("Four player are loaded ");
					board.printAllPlayersName();
					System.out.println();
					System.out.println("Board loaded with spaces like as below");
					board.printAllProperties();
					Utility.printStar();

					welcomePrint = false;
					start(true);
				}
				break;
				case "2": {
					board = new Board();
					rolls = new Rolls();

					init();
					Utility.printStar();
					System.out.println("You've Started simulating the monopoly game");
					System.out.printf("Four player are loaded ");
					board.printAllPlayersName();
					System.out.println();
					System.out.println("Board loaded with spaces like as below");
					board.printAllProperties();
					Utility.printStar();

					welcomePrint = false;
					start(false);
				}
				break;

				case "3": {
					welcomePrint = false;
					stopFlag = true;
					System.exit(0);
				}
				break;
				default:
					welcomePrint = false;
					Utility.printDash();
					System.out.println("Please choose correct option");
					Utility.printDash();
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
		rolls.loadRolls();

	}

	private static void start(boolean isRolls1) {
		boolean init = true;
		Utility.printDash();
		int totalRolls = isRolls1 ? rolls.getAllRollsOfDice1().size() : rolls.getAllRollsOfDice2().size();
		for (int i = 1; i <= (totalRolls - totalRolls % board.getPlayers().size()); i = i + 4) {
			int player2 = i + 1;
			int player3 = player2 + 1;
			int player4 = player3 + 1;

			int player1Dice1 = isRolls1 ? rolls.getAllRollsOfDice1().get(i) : rolls.getAllRollsOfDice2().get(i);

			int player2Dice1 = isRolls1 ? rolls.getAllRollsOfDice1().get(player2) : rolls.getAllRollsOfDice2().get(player2);

			int player3Dice1 = isRolls1 ? rolls.getAllRollsOfDice1().get(player3) : rolls.getAllRollsOfDice2().get(player3);

			int player4Dice1 = isRolls1 ? rolls.getAllRollsOfDice1().get(player4) : rolls.getAllRollsOfDice2().get(player4);

			int player1Total = (init) ? player1Dice1 + 1 : player1Dice1;
			int player3Total = (init) ? player3Dice1 + 1 : player3Dice1;
			int player2Total = (init) ? player2Dice1 + 1 : player2Dice1;
			int player4Total = (init) ? player4Dice1 + 1 : player4Dice1;
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

			System.out.println(
					"Player 1's Turn ( " + i + " ) Dice 1 ( " + player1Dice1 + " ) " + " Total ( " + player1Total + " )" + " Current Position ( " +
							currentPositionOfPlayer1 + " )" + " Peter's Balance ( " + balanceOfPlayer1 + " )");
			System.out.println(
					"Player 2's Turn ( " + player2 + " ) Dice 1 ( " + player2Dice1 + " ) " + " Total ( " + player2Total + " )" + " Current Position ( " +
							currentPositionOfPlayer2 + " )" + " Billy's Balance ( " + balanceOfPlayer2 + " )");
			System.out.println(
					"Player 3's Turn ( " + player3 + " ) Dice 1 ( " + player3Dice1 + " ) " + " Total ( " + player3Total + " )" + " Current Position ( " +
							currentPositionOfPlayer3 + " )" + " Charlotte's Balance ( " + balanceOfPlayer3 + " )");
			System.out.println(
					"Player 4's Turn ( " + player4 + " ) Dice 1 ( " + player4Dice1 + " ) " + " Total ( " + player4Total + " )" + " Current Position ( " +
							currentPositionOfPlayer4 + " )" + " Sweedal's Balance ( " + balanceOfPlayer4 + " )");

			if (balanceOfPlayer1 <= 0 || balanceOfPlayer2 <= 0 || balanceOfPlayer3 <= 0 || balanceOfPlayer4 <= 0) {
				printFooter();
//                System.exit(0);
				break;
			}
			Utility.printDash();
		}
	}

	private static void printFooter() {
		Utility.printHash();
		Utility.printStar();
		System.out.println();
		System.out.println("\t\t\t\t\t\t\tGame Over");
		System.out.println("\t\t\t\t\t\t----------------");
		System.out.println();

		System.out.println("\t\t\t* * *  1. Peter's Balance ( " + balanceOfPlayer1 + " )");
		System.out.println("\t\t\t* * *  2. Billy's Balance ( " + balanceOfPlayer2 + " )");
		System.out.println("\t\t\t* * *  3. Charlotte's Balance ( " + balanceOfPlayer3 + " )");
		System.out.println("\t\t\t* * *  4. Sweedal's Balance ( " + balanceOfPlayer4 + " ) ");
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
