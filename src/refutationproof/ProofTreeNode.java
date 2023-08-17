/*
 * ProofTreeNode.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 25 de Junho de 2007, 00:51
 */

package refutationproof;
import refutationproof.abstractsyntaxtree.Clause;
import javax.swing.*;



/*
   Class for implemeting the node of the proof tree, which is a Clause type.
*/
public class ProofTreeNode {
    
    protected ProofTreeNode parentA = null, parentB = null;
    protected Clause clausula;
    protected VariableInstancesTable table;
    
    
    /** Creates a new instance of NoArvoreProva */
    public ProofTreeNode() {
    }
    
    public ProofTreeNode(Clause clausula , ProofTreeNode parentA , ProofTreeNode parentB,
            VariableInstancesTable table)
    {
        this.parentA = parentA;
        this.parentB = parentB;
        this.clausula = clausula;
        this.table = table;
    }
    
    public Clause getClause()
    {
        return clausula;
    }
    
    public ProofTreeNode getParentA()
    {
        return parentA;
    }
            
    public ProofTreeNode getParentB()
    {
        return parentB;
    }
    
    public VariableInstancesTable getTable()
    {
        return table;
    }
    
    public String toString()
    {
        return clausula.toString();
    }
    
    
    /*
       As our proof tree is up-side down, converts it into a standard binary tree
    */
    public BinaryTree<Clause> buildTreeBottomUp(ProofTreeNode no)
    {
        if(no != null)
        {
            BinaryTree<Clause> novo =   new BinaryTree<Clause>(
                    no.getClause().instanciarVariaveis(no.getTable()));            
            BinaryTree<Clause> filhoA =  buildTreeBottomUp(no.getParentA());
            BinaryTree<Clause> filhoB =  buildTreeBottomUp(no.getParentB());
            if(filhoA != null)
            {
                novo.setLeftNode(filhoA);
            }
            if(filhoB != null)
            {
                novo.setRight(filhoB);
            }
            return novo;
        }
        else
        {
            return null;
        }
    }   
    
}
