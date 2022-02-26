package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lab4.gui.ConnectionWindow;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 * 
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;

	private JFrame frame;

	//The GUI components
	private GamePanel gameGridPanel;
	private JLabel messageLabel;
	private JButton connectButton;
	private JButton newGameButton;
	private JButton disconnectButton;

	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);


		frame = new JFrame("Gomoku");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		messageLabel = new JLabel("Welcome to Gomoku!");

		gameGridPanel = new GamePanel(g.getGameGrid());
		gameGridPanel.addMouseListener(mouseListener);


		connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				new ConnectionWindow();
				
			}
			
		});

		newGameButton = new JButton("New game");
		newGameButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				gamestate.newGame();
				
			}
			
		});

		disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				gamestate.disconnect();
				
			}
			
		});

		Box vBox = Box.createVerticalBox();
		vBox.add(gameGridPanel);
		vBox.add(disconnectButton);
		vBox.add(messageLabel);

		Box hBox = Box.createHorizontalBox();
		hBox.setAlignmentX(0); 
		hBox.add(connectButton);
		hBox.add(newGameButton);
		hBox.add(disconnectButton);

		frame.getContentPane().add(vBox);

		frame.setLocation(0, 0);
		frame.setVisible(true);

		frame.setSize(vBox.getPreferredSize().width + 20, vBox.getPreferredSize().height + hBox.getPreferredSize().height + 20);


	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
	}

	MouseListener mouseListener = new MouseListener(){
		public void mouseClicked(MouseEvent e) {
			//when player "me" clicked on board, we get the pixel coordinates
			int[] gridCoordinates = gameGridPanel.getGridPosition(e.getX(), e.getY());

			//When player "me" wants to make a move
			gamestate.move(gridCoordinates[0], gridCoordinates[1]);	
			}

			//Unecessary methods below - (Leave empty)

			public void mousePressed(MouseEvent e) {
      
			}
	
			public void mouseReleased(MouseEvent e) {
		  
			}
	
			public void mouseEntered(MouseEvent e) {
		  
			}
	
			public void mouseExited(MouseEvent e) {
		  
			}
		
		};
			
	}
