/**
 * @author Atyansh Jaiswal
 **/


import java.util.*;


/**
 *  Main Class that contains everything
 **/
public class Directory
{
  /**
   *  Driver method for the code.
   **/
  public static void main(String[] args)
  {
    Scanner scan = new Scanner(System.in);

    // Creating the root directory. With null as the parent.
    Node root = new Node("root", null);

    Node travel = root;

    Node sub;

    String line;
    String[] tokens;

    // Reading till no more input
    while(scan.hasNextLine())
    {
      line = scan.nextLine();
      System.out.println("Command: " + line);

      //Regex pattern to get tokens regardless of what kind of whitespace
      tokens = line.split("\\s+");


      // if conditions for different commands
      if(tokens[0].equals("ls"))
      {
        System.out.println("Directory of " + travel.getPath() + ":");

        if(travel.size() == 0)
          System.out.println("No subdirectories");
        else
        {
          System.out.println(travel.getSubs());
        }
      }
      else if(tokens[0].equals("up"))
      {
        travel = travel.getParent();

        if(travel == null)
        {
          System.out.println("Cannot move up from root directory");
          travel = root;
        }
      }
      else if(tokens[0].equals("mkdir"))
      {
        sub = new Node(tokens[1], travel);
        
        // Cannot add if duplicate
        if(!travel.add(sub))
          System.out.println("Subdirectory already exists");
        
        sub = null;
      }
      else if(tokens[0].equals("cd"))
      {
        sub = new Node(tokens[1], travel);

        if(travel.contains(sub))
          travel = travel.getSub(sub);
        else
          System.out.println("Subdirectory does not exist");

        sub = null;
      }
      else
      {
        System.err.println("INVALID. EXITING. " + tokens[0]);
        System.err.println("Potential Parsing error, maybe a problem with " + 
                           "tabs or spaces.");
        System.exit(-1);
      }
    }
  }
}


/**
 *  Class Node that represents each directory.
 *  Each directory contains a TreeSet that contains the subdirectories.
 **/
class Node implements Comparable<Node>
{
  private String name;        // Name of directory
  private TreeSet<Node> subs; // TreeSet ofsubdirectories
  private String path;        // Containing path of the directory
  private Node parent;        // Parent directory

  /**
   *  Constructor
   **/
  public Node(String name, Node parent)
  {
    this.name = name;
    this.parent = parent;

    if(parent == null)
      path = name;
    else
      path = parent.path + "\\" + name;

    subs = new TreeSet<Node>();
  }

  /**
   *  Used to compare to other directories (for TreeSet)
   **/
  public int compareTo(Node n)
  {
    return name.compareTo(n.name);
  }


  /**
   *  Used to compare to other directories, to check equality
   **/
  public boolean equals(Object o)
  {
    if(!(o instanceof Node))
      return false;

    Node other = (Node) o;

    return name.equals(other.name);
  }


  /**
   *  Return the number of subdirectories.
   **/
  public int size()
  {
    return subs.size();
  }


  /**
   *  Add a new subdirectories.
   **/
  public boolean add(Node n)
  {
    return subs.add(n);
  }


  /**
   *  Accessor for path
   **/
  public String getPath()
  {
    return path;
  }


  /**
   *  Get all subdirectories in String format
   **/
  public String getSubs()
  {
    String s = "";

    int count = 0;

    for(Node travel : subs)
    {
      if(count == 10)
      {
        s += "\n";
        count = 0;
      }

      s += (count++ == 0) ? travel.name : ("\t" + travel.name);
    }

    return s;
  }


  /**
   *  Check whether subdirectory exists
   **/
  public boolean contains(Node n)
  {
    return subs.contains(n);
  }


  /**
   *  Accessor for subdirectory
   **/
  public Node getSub(Node n)
  {
    for(Node travel : subs)
      if(travel.equals(n))
        return travel;

    return null;
  }


  /**
   *  Accessor for parent
   **/
  public Node getParent()
  {
    return parent;
  }

}
