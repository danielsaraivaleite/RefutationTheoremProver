/*
 * RefutationProof.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 25 de Junho de 2007, 00:46
 */

package refutationproof;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import refutationproof.abstractsyntaxtree.Clause;
import java.util.*;

/**
 * Main class of the Refutation Prover
 * @author Daniel
 */
public class RefutationProof {
    
    Vector<Clause> knowledgeBase = new Vector<Clause>();
    ProofTree tree;
    
    /** Creates a new instance of Provador */
    public RefutationProof() {
    }
    
    public void addClause(Clause premissa)
    {
        knowledgeBase.add(premissa);
    }
    
    public ProofTreeNode getFinalNode()
    {
        return tree.getFinalNode();        
    }
    
    /*
      Proves the theorem
    */
    public boolean proof()
    {
        
        VariableInstancesTable table = new VariableInstancesTable();
        
        tree = new ProofTree();
        for(int i = 0; i < knowledgeBase.size(); i++)
        {
            tree.addPremiseNode(new ProofTreeNode(knowledgeBase.get(i), null, null, table.clone()));            
        }
        
        return tree.proof();        
        
    }
        

    /*
     Main entrance of the program
    */
    public static void main(String[] args) 
    {
        
        String fileName = args[0];
        RefutationProof prover = new RefutationProof();
        
        StringBuffer fileBuffer;
        String fileString=null;
        String line;

        try 
        {
          FileReader in = new FileReader (fileName);
          BufferedReader dis = new BufferedReader (in);
          fileBuffer = new StringBuffer() ;

          while ((line = dis.readLine()) != null) 
          {
              // add each clause to the prover
              // comments are ignored
                fileBuffer.append (line + "\n");
                if(!line.trim().equals("") && !line.trim().startsWith("#")&&  !line.trim().startsWith("%") ) 
                        prover.addClause(Clause.parse(line));
          }
          in.close();
          fileString = fileBuffer.toString ();
          
          if(prover.proof()) 
          {
                System.out.println("Result: YES");
                System.out.println("Proof tree\n");
                System.out.println(prover.getFinalNode().buildTreeBottomUp(prover.getFinalNode()).toString());
                System.out.println("\nInstances:");
                System.out.println(prover.getFinalNode().getTable().toString());
                
          }
          else
          {
              System.out.println("Result: NO");
          }
          
        }
        catch  (IOException e ) {
          System.out.println("Error reading input file. Usage: RefutationProof <filename>");
        }


         
    }
    
    
}
