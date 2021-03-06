import java.util.ArrayList;
import java.util.List;

public class Treset {

    static ArrayList<Player> player;

    public static void main(String [] args) {
        boolean printOn = true;

        int itermax = 1000;
        System.out.println("0-MC, 1-random");
        int[] cumScore={0,0};
        long startTime = System.currentTimeMillis();
        for (int iter = 0; iter < itermax; iter++) {
            if(iter%(itermax/10)==0)
                System.out.println("Progress: "+iter+"%");

            State gameState = new State(printOn);

            player = new ArrayList<Player>();
            //player.add(new RandomPlayer(0,gameState.playerHands.get(0)));
            int id = 0;
            player.add(new MCPlayer(id, gameState.playerHands.get(id++),true,2000));
            //player.add(new MCPlayer(id, gameState.playerHands.get(id++),false));
            player.add(new HumanPlayer(id,gameState.playerHands.get(id++)));
            //player.add(new RandomPlayer(1, gameState.playerHands.get(1)));
            player.add(new MCPlayer(id, gameState.playerHands.get(id++),true,2000));
            player.add(new HumanPlayer(id,gameState.playerHands.get(id++)));
            //player.add(new MCPlayer(id, gameState.playerHands.get(id++),false));
            //player.add(new HumanPlayer(3,gameState.playerHands.get(3)));
            //player.add(new RandomPlayer(3, gameState.playerHands.get(3)));


            ArrayList<Card> previousRound = new ArrayList<Card>();


            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 4; j++) {

                    int prev = gameState.playerToMove;

                    Card move = player.get(gameState.playerToMove).nextCard(gameState);
                    String msg = "\n" + prev + "\t" + move + "\n\n";
                    //if(printOn)
                    //   ColorPrint.hand(move.color,msg);
                    gameState.doMove(move);
                    System.out.println("\n\n\n\n\n\n\n\n\n\n");
                }
                System.out.println("Previous round");
                for(int m=gameState.discards.size()-4;m<gameState.discards.size();m++){
                    ColorPrint.hand(gameState.discards.get(m).color,gameState.discards.get(m).toString()+"\n");
                }
                if(printOn)
                    System.out.println();

                if(printOn)
                    for (int k = 0; k < 4; k++) {
                        String msg = " [" + k + "] " + gameState.playerPoints[k];
                        ColorPrint.score(msg);
                    }
                String msg = "\n\n______________________________\nNew turn:";
                if(printOn)
                    ColorPrint.turn(msg);
            }
            ColorPrint.winner("Winner:"+gameState.winner() + "\n");
            cumScore[gameState.winner()]++;
        }
        System.out.printf("Win percentage: %.3f",(double)cumScore[0]/(double)itermax);


    }
}
