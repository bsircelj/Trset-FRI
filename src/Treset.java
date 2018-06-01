import java.util.ArrayList;
import java.util.List;

public class Treset {

    static ArrayList<Player> player;

    public static void main(String [] args){
        State gameState = new State();

        player = new ArrayList<Player>();
        player.add(new RandomPlayer(0,gameState.playerHands.get(0)));
        player.add(new RandomPlayer(1,gameState.playerHands.get(1)));
        player.add(new MCPlayer(2,gameState.playerHands.get(2)));
        player.add(new HumanPlayer(3,gameState.playerHands.get(3)));



        ArrayList<Card> previousRound = new ArrayList<Card>();

        for(int i=0;i<10;i++){

            for(int j=0;j<4;j++) {
                int prev = gameState.playerToMove;

                Card move = player.get(gameState.playerToMove).nextCard(gameState);
                System.out.print(prev+"\t"+move+"\n");
                gameState.doMove(move);
            }
            System.out.println("\nNew turn:");
        }

    }



}
