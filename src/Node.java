import java.util.ArrayList;
import java.util.TreeSet;

public class Node {

    int wins,visits,avails;

    ArrayList<Node> childNodes;
    Node parent;
    int playerJustMoved; //Kdo je na vrsti
    Card move; //Karto, ki jo igra kdor je na vrsti v tej potezi

    public Node(){//Samo za root node, vedno zacne igralec 0
        parent=null;
        move = null;
        childNodes = new ArrayList<Node>();
        playerJustMoved = 0;
    }

    public Node(Card move, Node parent, int justMoved){
        this.wins = 0;
        this.visits = 0;
        this.avails = 1;
        this.playerJustMoved=justMoved;
        childNodes = new ArrayList<Node>();
        this.parent=parent;
        this.move=move;
    }

    public ArrayList<Card> getUntriedMoves(ArrayList<Card> legalMoves){
        ArrayList<Card> untried = new ArrayList<Card>();
        for(Card move:legalMoves){
            if(!containsNode(this.childNodes,move)){
                untried.add(move);
            }
        }

        return untried;
    }
    public Node UCBSelectChild(ArrayList<Card> legalMoves){
        return UCBSelectChild(legalMoves,0.7);
    }
    public double UCBSGet(){
        return (double)this.wins/(double)this.visits + 0.7*Math.sqrt(Math.log((double)this.avails)/(double)this.visits);
    }

    public Node UCBSelectChild(ArrayList<Card> legalMoves, double exploration){
        ArrayList<Node> legalChildren = new ArrayList<Node>();
        for(Node child:this.childNodes){
            if(containsCard(legalMoves,child.move)){
                legalChildren.add(child);
            }
        }
        double score = -1;
        Node maxChild = null;
        for(Node child:legalChildren){
            double curScore = (double)child.wins/(double)child.visits + exploration*Math.sqrt(Math.log((double)child.avails)/(double)child.visits);
            if(curScore>score){
                score=curScore;
                maxChild=child;
            }
            child.avails++;
        }

        return maxChild;
    }

    public Node addChild(Card move, int p){
        Node n = new Node(move,this, p);
        if (!containsNode(childNodes,n))
            this.childNodes.add(n);
        return n;
    }

    public void Update(State terminalState){
        //Update this node - increment the visit count by one, and increase the win count by the result of terminalState for self.playerJustMoved.
        this.visits +=1;
        this.wins += terminalState.getResult(this.playerJustMoved);
    }

    public String treeToString(int indent){
        String s = this.indentString(indent) + this.toString();
        for (int i =0;i < this.childNodes.size(); i++){
            if (i == this.childNodes.size() - 1){
                s += childNodes.get(i).treeToString(indent + 1);
                s += "\n";
            } else {
                s += childNodes.get(i).treeToString(indent + 1);
            }
        }
    return s;

    }


    public String indentString(int indent){
        String s = "\n";

        for(int i =0; i < indent + 1; i++){
            if(i==0)
                s+='\t';
            s += "| ";
        }
        return s;
    }

    public String chilrenToString(){

        String s = "\n";

        for (int i = 0; i < this.childNodes.size(); i++) {
            s += childNodes.get(i).toString() + "\n";
        }

        return s;
    }

    public String toString(){
        try {
            return move.toString()+String.format(" %.10f",this.UCBSGet());
        } catch (NullPointerException npe){
            return "Root node";
        }
    }

    public static boolean containsNode(ArrayList<Node> n, Card that){
        for(Node all:n){
            if(all.move.equals(that))
                return true;
        }
        return false;
    }

    public static boolean containsNode(ArrayList<Node> n, Node that){
        for(Node all:n){
            if(all.equals(that))
                return true;
        }
        return false;
    }

    public static boolean containsCard(ArrayList<Card> deck, Card that){
        for(Card all:deck){
            if(all.equals(that))
                return true;
        }
        return false;
    }

    public boolean equals(Node that){
        return this.move.equals(that.move)&&this.playerJustMoved==that.playerJustMoved;
    }


}
