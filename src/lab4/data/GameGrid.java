package lab4.data;

import java.util.Observable;

public class GameGrid extends Observable {
	private int[][] gameGrid;
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	private int INROW = 5;

	public GameGrid(int size) {
		gameGrid = new int[size][size];
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				gameGrid[x][y] = 1;
			}

		}
	}

	public int getLocation(int x, int y) {

		return gameGrid[x][y];
	}

	public int getSize() {
		return gameGrid.length;
	}

	public boolean move(int x, int y, int player) {
		if (gameGrid[x][y] == EMPTY) {

			gameGrid[x][y] = player;
			setChanged();
			notifyObservers();

			return true;
		} else {
			return false;
		}
	}

	public void clearGrid() {
		for (int x = 0; x < gameGrid.length; x++) {
			for (int y = 0; y < gameGrid.length; y++) {
				gameGrid[x][y] = 0;

			}
		}
		setChanged();
		notifyObservers();
	}

public boolean isWinner(int player) {
	for (int y=0;y<gameGrid.length;y++) {
		for(int x=0;x<gameGrid.length;x++) {
			if(gameGrid[x][y]==player) {
				int count=1;
				if(x+INROW-1<gameGrid.length) {
				
					for(int i=1;i<INROW;i++) {
						if (gameGrid[x+i][y]==player) {
							count++;
						}
						}
					if(count==INROW) {
						return true;
					}
					}
					if(y+INROW-1<gameGrid.length) {
					
						for(int i=1;i<INROW;i++) {
							if (gameGrid[x][y+i]==player) {
								count++;
							}
							}
						if(count==INROW) {
							return true;
									}
					}
						if((y+INROW-1<gameGrid.length)&&(x+INROW-1<gameGrid.length)) {
							
							for(int i=1;i<INROW;i++) {
								if (gameGrid[x+i][y+i]==player) {
									count++;
								}
								}
							if(count==INROW) {
								return true;
			}
					
		}
		
}
}
		}		

	
	return false;
}
}