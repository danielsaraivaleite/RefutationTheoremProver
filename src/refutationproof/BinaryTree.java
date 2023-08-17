/*
 * ProofTree.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 25 de Junho de 2007, 00:51
 */


package refutationproof;

/*
   Class for implemeting a simple binary tree
*/
public class BinaryTree<T>
{

    private T nodeValue;
    private BinaryTree parent; 
    private BinaryTree leftNode;
    private BinaryTree rightNode; 

    
    private BinaryTree()
    {
	nodeValue = null;
	parent = null; 
        leftNode = this;
        rightNode = this;
    }


    public BinaryTree(T value)
    {
	nodeValue = value;
	parent = null;
	leftNode = null;
        rightNode = null;
    }


    public BinaryTree(T nodeValue, BinaryTree<T> leftTree, BinaryTree<T> rightTree)
    {
	this(nodeValue);
	setLeftNode(leftTree);
	setRight(rightTree);
    }


    public BinaryTree getLeftNode()
    {
	return leftNode;
    }


    public BinaryTree getRightNode()
    {
	return rightNode;
    }


    public BinaryTree getParentNode()
    {
	return parent;
    }
    

    public void setLeftNode(BinaryTree pLeftNode)
    {
	leftNode = pLeftNode;
	leftNode.setParent(this);
    }


    public void setRight(BinaryTree pRightNode)
    {
	rightNode = pRightNode;
	rightNode.setParent(this);
    }


    protected void setParent(BinaryTree parentNode)
    {
	parent = parentNode;
    }


    public T getNodeValue()
    {
	return nodeValue;
    }


    public void setValue(T value)
    {
	nodeValue = value;
    }
    
    /*
    Adapted from: https://www.baeldung.com/java-print-binary-tree-diagram
    */
    public void traversePreOrder(StringBuilder sb, String padding, String pointer)
    {
        BinaryTree<T> tree = this;
        if (tree != null ) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(tree.getNodeValue());
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("│  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (tree.getRightNode() != null) ? "├──" : "└──";
            
            if(tree.getLeftNode() != null) 
            {
                tree.getLeftNode().traversePreOrder(sb, paddingForBoth, pointerForLeft );
            }
            if(tree.getRightNode()!=null ) 
            {
                tree.getRightNode().traversePreOrder(sb, paddingForBoth, pointerForRight);
            }

        }
    }

    /*
     Pretty print of a tree using pre order traversal
    */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        this.traversePreOrder(sb, "", "");
        return sb.toString();
    }
}
