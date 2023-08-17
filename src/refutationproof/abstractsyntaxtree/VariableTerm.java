/*
 * VariableTerm.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 16 de Junho de 2007, 21:33
 */

package refutationproof.abstractsyntaxtree;

/**
 * A Variable term of the parser
 * @author Daniel
 */
public class VariableTerm extends Term {
    
    protected String nome;
    
    /** Creates a new instance of TermoVariavel */
    public VariableTerm(String ASnome) {
        nome = ASnome;
    }
    
    public String toString()
    {
        return nome;
    }
    
    public String getName()
    {
        return nome;
    }
    
    public void rename(String newName)
    {
        nome = newName;
    }
    
    public Term clone()
    {
        return new VariableTerm(nome);
    }
    
    public boolean equals(Term termo)
    {
        if(termo instanceof VariableTerm)
        {
            return nome.equals(((VariableTerm)termo).nome);
        }
        return false;
    }
    
}
