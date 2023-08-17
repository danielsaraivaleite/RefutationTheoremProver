/*
 * ProofTree.java
 * Parte of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 25 de Junho de 2007, 00:51
 */

package refutationproof;
import java.util.*;

/*
   Class for implemeting the proof tree
   (which is build up-side down)
*/
public class ProofTree {
    
    protected LinkedList<ProofTreeNode> queue;
    protected int max_height = 11;
    protected Resolution resolution = new Resolution();
    protected ProofTreeNode lastNode;
    
    public ProofTree() {
        queue = new LinkedList<ProofTreeNode>();
    }
    
    public void addPremiseNode(ProofTreeNode premise) {
        queue.addFirst(premise);
    }
    
    public ProofTreeNode getFinalNode()
    {
        return lastNode;
    }
    
    public boolean proof() {
        lastNode = null;
        return expand(0);
    }
    
    public boolean expand(int h) 
    {
        //System.out.printf("Expandindo nível %d\n", h);
        boolean createdNew = false;
        if(h <= max_height) 
        {
            // expand all possible combinations
            Vector<ProofTreeNode> novos = new Vector<ProofTreeNode>();
            for(int i = 0; i < queue.size() - 1; i++) 
            {
                for(int j = i + 1; j < queue.size(); j++ ) 
                {
                    // Check compatibility between tables
                    VariableInstancesTable tabela = new VariableInstancesTable();
                    if(VariableInstancesTable.merge(queue.get(i).getTable(), queue.get(j).getTable(), tabela)) 
                    {
                        resolution.applyResolution(queue.get(i).getClause(), queue.get(j).getClause(), tabela);
                        VariableInstancesTable tabelaResolucao;
                        if(resolution.success()) 
                        {
                            tabelaResolucao = resolution.getVariableInstancesTable();
                            ProofTreeNode novoNo = new ProofTreeNode(resolution.recuperaResolvente(), queue.get(i), queue.get(j), tabelaResolucao); 
                            novos.add(novoNo);
                            //System.out.println("novo: " + novoNo);
                            if(resolution.gotNil())
                            {                                
                                lastNode = novoNo;
                                return true;
                            }
                        }
                    }
                }
            }
            // Add new nodes to begining
            for(int i = novos.size() - 1; i >= 0; i--)
            {
                if(!queue.contains(novos.get(i)))
                {
                    createdNew = true;
                    queue.addFirst(novos.get(i));                    
                }                
            }
            if(createdNew)
            {
                return expand(h + 1);                
            }
            else
            {
                return false;
            }            
        }
        else 
        {
            System.out.println("Maximum heigth achieved.");
            return false;
        }
    }
}
