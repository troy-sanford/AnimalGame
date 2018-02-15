import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Node {
    private Node left;
    private Node right;
    private boolean truth;
    private String data;


    public Node(String s) {
        left = null;
        right = null;
		truth = false;
        data = s;
    }

    public Node(boolean b, String s) {
	left = null;
	right = null;
	truth = b;
	data = s;
    }

    public boolean isLeaf() {
	if (getRight() == null && getLeft() == null)
		return true;
	return false;
    }

    public void setLeft(Node n) {
        left = n;
    }

    public void setRight(Node n) {
        right = n;
    }

    public void setTruth(boolean b) {
	truth = b;
    }

    public void setData(String d) {
        data = d;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public boolean getTruth() {
	return truth;
    }

    public String getData() {
        return data;
    }
}

class BinaryTree {
    private Node root;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public BinaryTree() {
        root = null;
    }

    public String getInput() {
        String s = "";
        try {
            s = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public boolean isTrue(char c) {
        char[] trueValues = {'T','t','Y','y','1'};
        char[] falseValues = {'F','f','N','n','0'};

        for (int i=0; i<trueValues.length; i++) {
            if (c == trueValues[i]) {
                return true;
            }
        }
        for (int i=0; i<falseValues.length; i++) {
            if (c == falseValues[i]) {
                return false;
            }
        }
        System.out.println("I'm not sure what you mean. Please answer again.\nY/N: ");
        return isTrue(getInput().charAt(0));
    }

    public void makeRoot(Node n) {
	root = n;
    }

    public void insert(Node p, Node c) {
        if (c.getTruth() == false)
            p.setLeft(c);
        else if (c.getTruth() == true)
            p.setRight(c);
    }

    // Called when inserting from a leaf Node
    // (When the animal guessed is incorrect)
    public void insert(Node n) {
	String rightData = "";
	String newData = "";

	System.out.print("What were you thinking of?: ");
	rightData = "Were you thinking of a " + getInput() + "?";

	System.out.println("\nHere's what I got: " + rightData + "\n");

	System.out.print("What's a question I can ask about that? (Answer must be true): ");
	newData = getInput();
	System.out.println("\nHere's what I got: " + newData + "\n");

	n.setLeft(new Node(n.getTruth(), n.getData()));
	n.setData(newData);
	n.setRight(new Node(true, rightData));
	System.out.println("Let's start over!...\n");
	play(root);
    }

    public void inOrder() {
        inOrder(root);
    }

    public void inOrder(Node n) {
        if (n != null) {
            inOrder(n.getLeft());
            System.out.println(n.getData());
            inOrder(n.getRight());
        }
    }

    public void play() {
	play(root);
    }

    public void play(Node n) {
        System.out.println(n.getData());
        System.out.print("Y/N: ");
        if (isTrue(getInput().charAt(0))) {
            if (n.getRight() == null) {
                System.out.print("I got it!\n");
            }
            else {
                play(n.getRight());
            }
        }
        else {
            if (n.getLeft() == null) {
                insert(n);
            }
            else {
                play(n.getLeft());
            }
        }

    }
}

public class AnimalGame {
    public static void main(String[] args) {
	BinaryTree tree = new BinaryTree();
	Node root = new Node("Is it big?");
	Node right = new Node(true, "Does it have tusks?");
	Node left = new Node(false, "Is it a mammal?");
	tree.makeRoot(root);
	tree.insert(root, right);
	tree.insert(root, left);
	tree.insert(right, new Node(true, "Were you thinking of an elephant?"));
	tree.insert(right, new Node(false, "Were you thinking of a whale?"));
	tree.insert(left, new Node(true, "Were you thinking of a mouse?"));
	tree.insert(left, new Node(false, "Were you thinking of an ant?"));
	tree.play();
    }
}
