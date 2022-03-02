package lab4;
import lab4.gui.GomokuGUI;
import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;
public class GomokuMain {

	public static void main(String[] args) {
		int port;
		if (args.length == 1) {
			port = Integer.parseInt(args[0]);
		}
		else {
			port = 4000;
		}
		GomokuClient client=new GomokuClient(port);
		GomokuGameState state=new GomokuGameState(client);
		GomokuGUI GUI = new GomokuGUI(state,client);
	}

}
