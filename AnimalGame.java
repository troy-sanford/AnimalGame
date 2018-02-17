import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;

class Node implements Serializable {
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

class BinaryTree implements Serializable {
    private Node root;

    public BinaryTree() {
        root = null;
    }

    public String getInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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

    while (true) {
        System.out.print("What were you thinking of?: ");
	    rightData = "Were you thinking of a " + getInput() + "?";
        System.out.print("\nHere's what I got: " + rightData + "\nIs that correct (Y/N)?: ");
        if (isTrue(getInput().charAt(0))) {
            System.out.println("Okay, got it!");
            break;
        }
    }

    while (true) {
        System.out.print("What's a question I can ask about that? (Answer must be true): ");
        newData = getInput();
        System.out.print("\nHere's what I got: " + newData + "\nIs that correct (Y/N)?: ");
        if (isTrue(getInput().charAt(0))) {
            System.out.println("Okay, got it!");
            break;
        }
    }

	n.setLeft(new Node(n.getTruth(), n.getData()));
	n.setData(newData);
	n.setRight(new Node(true, rightData));
	System.out.println("Thanks, I'll remember that for next time!...\n");
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
                System.out.println("\nI got it!\n");
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

    String dir = System.getProperty("user.dir") + "/animal_info.ser";

    BinaryTree tree = load(dir);
    
    tree.play();
    
    save(tree, dir);

    }

    public static BinaryTree load(String dir) {
        BinaryTree tree = new BinaryTree();
        try {
            FileInputStream fileIn = new FileInputStream(dir);
            ObjectInputStream in = new ObjectInputStream(fileIn);
    
            tree = (BinaryTree) in.readObject();
    
            in.close();
            fileIn.close();
            System.out.println("Loaded successfully!");

        } catch(FileNotFoundException e) {
            Node root = new Node("Is it big?");
            Node right = new Node(true, "Were you thinking of an elephant?");
            Node left = new Node(false, "Were you thinking of an ant?");
            tree.makeRoot(root);
            tree.insert(root, right);
            tree.insert(root, left);
            System.out.println("Load failed. Created new tree\n");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static void save(BinaryTree tree, String dir) {
        try {
            FileOutputStream fileOut = new FileOutputStream(dir);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(tree);
            out.close();
            fileOut.close();
            System.out.println("Saved successfully to directory " + dir);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
