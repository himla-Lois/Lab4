/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;


/**
 * Represents the state of a game
 * @author Wilma
 * @author Noora
 * @author Rasmus
 */
public class GomokuGameState extends Observable implements Observer {
	
	// Game variables
	private GameGrid gameGrid;
	private final int DEAFULT_SIZE = 15;
	public int getDEFAULT_SIZE() {
		return DEAFULT_SIZE;
	}

	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED = 3;
	private int currentState;
	private String message;
	private GomokuClient client;

	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */

	public GomokuGameState(GomokuClient gc) {
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEAFULT_SIZE);
	}

	/**
	 * Return the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString() {
		return message;
	}

	/**
	 * return the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid() {
		return gameGrid;
	}

	  /**
	   * This player makes a move at a specified location
	   * 
	   * First, check if the games has started or
	   * Then check if it's not your turn tell the player.  (currentState is OTHER_TURN) 
	   * If move makes the game end (by winning), end the game!
	   * 
	   * @param x the x coordinate
	   * @param y the y coordinate
	   */
	
	public void move(int x, int y) {
		//check if the games has not started
		if (currentState == NOT_STARTED) {
			message = "Game not started";
			
			//if it's not your turn tell the player. 
		} else if (currentState == OTHER_TURN) {
			message = "it is not your turn yet";
		}
		//appropriate response if it's your turn
		else if (currentState == MY_TURN) {
			if (gameGrid.move(x, y, gameGrid.ME)) {
				client.sendMoveMessage(x, y);
				if (gameGrid.isWinner(gameGrid.ME)) {
					currentState = FINISHED;
					message = "You won!";
				} else {
					currentState = OTHER_TURN;
					message = "waiting for other player";
				}
			} else {
				message = "Not a legal move";

			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * start a new game with the current client
	 */
	public void newGame() {

		gameGrid.clearGrid();
		currentState = OTHER_TURN;
		message = "waiting for other player";
		client.sendNewGameMessage();
		setChanged();
		notifyObservers();
	}

	/**
	 * other player has requested a new game,so the game state is changed
	 * accordingly
	 */

	public void receivedNewGame() {

		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "your turn!";
		setChanged();
		notifyObservers();
	}

	/**
	 * The connection to the other player is lost, so the game is interrupted
	 */
	public void otherGuyLeft() {

		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "other player disconnected!";
		setChanged();
		notifyObservers();
	}

	/**
	 * the player disconnect from the client
	 */
	public void disconnect() {

		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		client.disconnect();
		message = "Player disconnected!";
		setChanged();
		notifyObservers();
	}

	/**
	 * The player receives a move from the other player
	 * 
	 * @param x the x coordinate of the move
	 * @param y the y coordinate of the move
	 */
	public void receivedMove(int x, int y) {
		gameGrid.move(x, y, gameGrid.OTHER);
		if (gameGrid.isWinner(gameGrid.OTHER)) {
			currentState = FINISHED;
			message = "You lost!";
		} else {
			currentState = MY_TURN;
			message = "your turn!";
		}
		setChanged();
		notifyObservers();
	}

	
	  /** 
	   * Method predefined by H�kan to update the game state based on receiving a connection
	   * 
	   * @param o
	   * @param arg
	   */
	public void update(Observable o, Object arg) {
		switch (client.getConnectionStatus()) {
		case GomokuClient.CLIENT:
			message = "Game started,it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:

			message = "Game started,waiting for other player!";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();

	}

}
