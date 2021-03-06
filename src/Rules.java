import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Rules {

    public static ArrayList<Card> createDeck(Random random){//Naredi nov deck in ga zmesa
        ArrayList<Card> deck = new ArrayList<Card>();
        char[] all = {'S','B','C','D'};
        for(int i=1;i<=10;i++){
            for(char color:all)
                deck.add(new Card(i,color));
        }
        Collections.shuffle(deck,random);
        return deck;
    }

    public static ArrayList<Card> deckPart(ArrayList<Card> deck, int i){
        return new ArrayList<Card>(deck.subList(i*10,(i+1)*10));
    }

    public static int pickUp(ArrayList<Card> table, int [] order){//Kdo pobira trenuno rundo
        int which=0;                         //Vrne indeks zmagovalnega igralca

        char firstColor = table.get(which).color;
        for(int i=1;i<4;i++) {
            if (table.get(i).color == firstColor) {
                if (table.get(i).rank > table.get(which).rank) {
                    which = i;
                }
            }
        }

        return order[which];
    }

    public static int[] changeOrder(int [] lastOrder, ArrayList<Card> onTable){
        int lastPicked = Rules.pickUp(onTable, lastOrder);
        int[] newOrder = new int[4];
        for (int i = lastPicked; i < lastPicked + 4; i++ ) {
            newOrder[i - lastPicked] = i % 4;
        }
        return newOrder;
    }

    public static void removeAll(ArrayList<Card> deck, ArrayList<Card> removed){
        for(Card remove:removed){
           remove(deck,remove);
        }
    }

    public static void remove(ArrayList<Card> deck, Card removed){
        int i = 0;
        while(true){
            if(i>=deck.size())
                break;
            if(removed.equals(deck.get(i))){
                deck.remove(i);
                break;} //Kaj nebi blo smislno
            else
                i++;
        }
    }

}
