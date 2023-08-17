/*
 * VariableInstancesTable.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 17 de Junho de 2007, 18:40
 */

package refutationproof;
import refutationproof.abstractsyntaxtree.VariableTerm;
import refutationproof.abstractsyntaxtree.ConstantTerm;
import refutationproof.abstractsyntaxtree.Term;
import java.util.*;


/**
 * Implements a variable hash table
 * @author Daniel
 */
public class VariableInstancesTable {
    
    protected Hashtable<String, Term> table;
    protected Vector<HashSet<String>> linkedVariables;
    
    /** Creates a new instance of TabelaInstanciacoes */
    public VariableInstancesTable() {
        table =  new Hashtable<String, Term>();
        linkedVariables = new Vector<HashSet<String>>();
    }
    
    public VariableInstancesTable(Hashtable<String, Term> instantiations, Vector<HashSet<String>> links ) {
        table =  instantiations;
        linkedVariables = links;
    }    
    
    public Hashtable<String, Term> getInstantiatedVariablesTable()
    {
        return table;
    }
    
    public Vector<HashSet<String>> getLinkedVariablesTable()
    {
        return linkedVariables;
    }    
    
    public static boolean merge(VariableInstancesTable tabA, VariableInstancesTable tabB, VariableInstancesTable tabSaida)
    {
        Enumeration<String> enumChaves = tabA.getInstantiatedVariablesTable().keys();
        while(enumChaves.hasMoreElements())
        {
            String varName = enumChaves.nextElement();
            if(!tabSaida.instantiate(varName, tabA.getInstantiatedVariablesTable().get(varName)))
            {
                return false;
            }
        }
        enumChaves = tabB.getInstantiatedVariablesTable().keys();
        while(enumChaves.hasMoreElements())
        {
            String varName = enumChaves.nextElement();
            if(!tabSaida.instantiate(varName, tabB.getInstantiatedVariablesTable().get(varName)))
            {
                return false;
            }
        }
        // insert linked variables
        Vector<HashSet<String>> linkedVariables = tabA.getLinkedVariablesTable();
        for(int i = 0; i < linkedVariables.size(); i++)
        {
            HashSet<String> setVars = linkedVariables.get(i);
            Vector<String> vectVars = new Vector<String>();
            vectVars.addAll(setVars);   
            for(int j = 0; j < vectVars.size() - 1; j++)
            {
                if(!tabSaida.instantiate(vectVars.get(j), new VariableTerm(vectVars.get(j + 1))))
                {
                    return false;
                }
            }
        }
        linkedVariables = tabB.getLinkedVariablesTable();
        for(int i = 0; i < linkedVariables.size(); i++)
        {
            HashSet<String> setVars = linkedVariables.get(i);
            Vector<String> vectVars = new Vector<String>();
            vectVars.addAll(setVars);   
            for(int j = 0; j < vectVars.size() - 1; j++)
            {
                if(!tabSaida.instantiate(vectVars.get(j), new VariableTerm(vectVars.get(j + 1))))
                {
                    return false;
                }
            }
        }        
        return true;         
    }
    
    
    public VariableInstancesTable clone()
    {
        // copia variaveis amarradas
        Vector<HashSet<String>> varLig = new Vector<HashSet<String>>();
        for(int i = 0; i < linkedVariables.size(); i++)
        {
            varLig.add((HashSet<String>)(linkedVariables.elementAt(i).clone()));   
        }
        // copia variaveis ligadas
        Hashtable<String, Term> varInst =  (Hashtable<String, Term>)this.table.clone();
        return  new VariableInstancesTable(varInst, varLig);                
    }
    
    public void assign(VariableInstancesTable tab)
    {
        table = tab.table;
        linkedVariables = tab.linkedVariables;
    }
    
    public boolean instantiate(String variavel, Term termo)
    {
        // linking to a variable
        if(termo instanceof VariableTerm)
        {
            VariableTerm termoVariavel = (VariableTerm)termo;
            if(table.containsKey(variavel))
            {
                return instantiate(termoVariavel.getName(), table.get(variavel));
            }
            else
            {
                if(table.containsKey(termoVariavel.getName()))
                {
                    return instantiate(variavel, table.get(termoVariavel.getName()));
                }
                else // will create or update a hashset of linked variables ligadas
                {
                    int i = 0;
                    int j = 0;
                    boolean encontrouA = false;
                    boolean encontrouB = false;
                    while(i < linkedVariables.size())
                    {
                        if(linkedVariables.elementAt(i).contains(variavel))
                        {
                            encontrouA = true;
                        }
                        i++;
                    }
                    while(j < linkedVariables.size())
                    {
                        if(linkedVariables.elementAt(j).contains(termoVariavel.getName()))
                        {
                            encontrouB = true;
                        }
                        j++;
                    }
                    HashSet<String> novoHash = new HashSet<String>();
                    novoHash.add(variavel);
                    novoHash.add(termoVariavel.getName());
                    if(encontrouA)
                    {
                        novoHash.addAll(linkedVariables.elementAt(i-1)); 
                        linkedVariables.remove(i-1);
                    }
                    if(encontrouB)
                    {
                        if(i != j)
                        {
                            novoHash.addAll(linkedVariables.elementAt(j-1));
                            linkedVariables.remove(j-1);
                        }                                               
                    }
                    linkedVariables.add(novoHash);                    
                    return true;
                }
            }
        }
        else // linking to an atomic expression
        {            
            ConstantTerm termoConstante = (ConstantTerm)termo;
            if(table.containsKey(variavel)) // ja existe um atomo lgado, deve ser =
            {
                return table.get(variavel).equals(termoConstante);
            }
            else // does not contain the variable yet
            {
                table.put(variavel, termo);
                // link to eventual other variables
                int i = 0;
                boolean found = false;
                while(i < linkedVariables.size() && !found)
                {
                    if(linkedVariables.elementAt(i).contains(variavel))
                    {
                        found = true;
                    }
                    i++;
                }
                if(found)
                {
                    HashSet<String> links = linkedVariables.elementAt(i-1);
                    linkedVariables.remove(i - 1);
                    Iterator<String> iter = links.iterator();
                    while(iter.hasNext())
                    {
                        String var = iter.next();
                        if(!variavel.equals(var))
                        {
                            return instantiate(var, termo);
                        }
                    }
                }
                return true;
            }
        }
    }    
    
    public boolean checkInstanceVariableAtom(String variavel)
    {
        return table.containsKey(variavel);
    }
    
    public ConstantTerm getTermLinkedToVariable(String variavel)
    {
        ConstantTerm termo = (ConstantTerm)table.get(variavel);
        return termo;
    }
    
    public String getVariableValue(String variavel)
    {
        ConstantTerm termo = (ConstantTerm)table.get(variavel);
        return termo.recuperaValor();
    }    
    
    public String getMostGeneralVariableName(String variavel)
    {
        String name = variavel;
        for(int i = 0; i < linkedVariables.size(); i++)
        {
            if(linkedVariables.elementAt(i).contains(variavel))
            {
                Iterator<String> it =  linkedVariables.elementAt(i).iterator();
                return it.next();
            }
        }
        return name;
    }
    
    
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();   
        // known
        Enumeration<String> enuInst = table.keys();
        while(enuInst.hasMoreElements())
        {
            String variavel = enuInst.nextElement();
            buffer.append(variavel + " / " + table.get(variavel).toString()+"\n");
        }
        for(int i = 0; i < linkedVariables.size(); i++)
        {
            buffer.append(linkedVariables.elementAt(i).toString() + " / unknown\n");
        }
        return buffer.toString();
    }
}
