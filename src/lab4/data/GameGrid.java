package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 * @author Wilma
 * @author Noora
 * @author Rasmus
 */
public class GameGrid extends Observable {
	private int[][] grid;
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	private int INROW = 7;

	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size) {
		grid = new int[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				grid[x][y] = EMPTY;
			}

		}
	}
	
	  /**
	   * Reads a location of the grid
	   * 
	   * @param x The x coordinate
	   * @param y The y coordinate
	   * @return the value of the specified location
	   */
	public int getLocation(int x, int y) {

		return grid[x][y];
	}


	  /**
	   * Returns the size of the grid
	   * 
	   * @return the grid size
	   */
	public int getSize() {
		return grid.length;
	}

	 /**
	   * Enters a move in the game grid
	   * 
	   * @param x      the x position
	   * @param y      the y position
	   * @param player
	   * @return true if the insertion worked, false otherwise
	   */
	public boolean move(int x, int y, int player) {
		if (grid[x][y] == EMPTY) {

			grid[x][y] = player;
			setChanged();
			notifyObservers();

			return true;
		} else {
			return false;
		}
	}

	  /**
	   * Clears the grid of pieces
	   */
	public void clearGrid() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid.length; y++) {
				grid[x][y] = EMPTY;

			}
		}
		setChanged();
		notifyObservers();
	}

	  /**
	   * Check if a player has X in row
	   * 
	   * @param player the player to check for
	   * @return true if player has 5 in row, false otherwise
	   */
	public boolean isWinner(int player) {
		

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				if (grid[x][y] == player) {
					
					//Looks Horizontally
					if (isWinnerX(x, y, player)) {
						return true;
					}
					
					//Look vertically
					if (isWinnerY(x, y, player)) {
						return true;
					}
					
					//Looks diagonally down to the right
					if (isWinnerDownRight(x, y, player)) {
						return true;
					}
			
					
					//Looks diagonally down to the left
					if (isWinnerDownLeft(x, y, player)) {
						return true;
					}
				
				}
			}
		}

		return false;
	}
	
	private boolean isWinnerX(int x, int y, int player) {
		
		int count = 1;
		if (x + INROW - 1 < grid.length) {
			for (int i = 1; i < INROW; i++) {
				if (grid[x + i][y] == player) {
					count++;
				} else break;
				if (count >= INROW) {
					return true;
				} 
			}
		} 
		return false;
	}
	
	private boolean isWinnerY(int x, int y, int player) {
		
		int count = 1;
		if (y + INROW - 1 < grid.length) {
			for (int i = 1; i < INROW; i++) {
				if (grid[x][y + i] == player) {
					count++;
				} else break;
				if (count >= INROW) {
					return true;
				} 
			}
		} 
		return false;
	}
	
	private boolean isWinnerDownRight(int x, int y, int player) {
		
		int count = 1;
		if ((y + INROW - 1 < grid.length) && (x + INROW - 1 < grid.length)) {
			for (int i = 1; i < INROW; i++) {
				if (grid[x + i][y + i] == player) {
					count++;
				} else break;
				if (count >= INROW) {
					return true;
				} 
			}
		} 
		return false;
		
	}
	
	private boolean isWinnerDownLeft(int x, int y, int player) {
		
		int count = 1;
		if ((y + INROW - 1 < grid.length) && (x - (INROW - 1) >= 0)) {
			for (int i = 1; i < INROW; i++) {
				if (grid[x - i][y + i] == player) {
					count++;
				} else break;
				if (count >= INROW) {
					return true;
				} 
			}
		}  
		return false;
	}
}