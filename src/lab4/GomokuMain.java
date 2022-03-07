package lab4;
import lab4.gui.GomokuGUI;
import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
public class GomokuMain {

	public static void main(String[] args) {
		int port;
		if (args.length == 1) {
			port = Integer.parseInt(args[0]);
		}
		else {
			port = 4000; //default port
		}
		GomokuClient client=new GomokuClient(port);
		GomokuGameState state=new GomokuGameState(client);
		GomokuGUI GUI = new GomokuGUI(state,client);
		

			//The other client window :))
		GomokuClient client2=new GomokuClient(5000);
		GomokuGameState state2=new GomokuGameState(client2);
		GomokuGUI GUI2 = new GomokuGUI(state2,client2);
	}
	
	

}
