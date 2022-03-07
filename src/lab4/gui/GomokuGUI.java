package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import lab4.gui.ConnectionWindow;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;

/**
 * The GUI class that handles the game window and its components
 * @author Wilma
 * @author Noora
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
		SpringLayout layout = new SpringLayout();
		frame.setLayout(layout);

    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		messageLabel = new JLabel("Welcome to Gomoku!");

		gameGridPanel = new GamePanel(g.getGameGrid());
		

		// When connect button is pressed, open a connection window
		connectButton = new JButton("Connect");
		newGameButton = new JButton("New game");
		disconnectButton = new JButton("Disconnect");


		frame.setLocation(0, 0);

		// vBox contains all elements properly aligned, so its preferred size is the minimum necessary size//
		//frame.setSize(vBox.getPreferredSize().width + 20, vBox.getPreferredSize().height + hBox.getPreferredSize().height + 20);
		gameGridPanel.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e) {
				//when player "me" clicked on board, we get the pixel coordinates
				int[] gridCoordinates = gameGridPanel.getGridPosition(e.getX(), e.getY());

				//When player "me" wants to make a move
				gamestate.move(gridCoordinates[0], gridCoordinates[1]);	
				}
		});

		connectButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				ConnectionWindow cv = new ConnectionWindow(client);
				
			}
			
		});
		newGameButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				gamestate.newGame();
				
			}
			
		});

	
		disconnectButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				gamestate.disconnect();
				
			}
			
		});

		frame.add(gameGridPanel);
		frame.add(connectButton);
		frame.add(newGameButton);
		frame.add(disconnectButton);
		frame.add(messageLabel);

		frame.pack();

		frame.setSize(gameGridPanel.getUNIT_SIZE() * gamestate.getDEFAULT_SIZE() + 40, gameGridPanel.getUNIT_SIZE() * gamestate.getDEFAULT_SIZE() + 200);

		layout.putConstraint(SpringLayout.WEST, gameGridPanel, 5, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.NORTH, gameGridPanel, 5, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.NORTH, connectButton, 5, SpringLayout.SOUTH, gameGridPanel);
		layout.putConstraint(SpringLayout.NORTH, newGameButton, 5, SpringLayout.SOUTH, gameGridPanel);
		layout.putConstraint(SpringLayout.NORTH, disconnectButton, 5, SpringLayout.SOUTH, gameGridPanel);

		layout.putConstraint(SpringLayout.WEST, messageLabel, 5, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.NORTH, messageLabel, 5, SpringLayout.SOUTH, connectButton);

		layout.putConstraint(SpringLayout.WEST, connectButton, 5, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.WEST, newGameButton, 5, SpringLayout.EAST, connectButton);
		layout.putConstraint(SpringLayout.WEST, disconnectButton, 5, SpringLayout.EAST, newGameButton);

		frame.setVisible(true);




	}
	
	  /**
	   * Method predefined by Håkan to update the buttons in the GUI based on the connection status
	   * 
	   * @param arg0
	   * @param arg1
	   */
	
	
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
