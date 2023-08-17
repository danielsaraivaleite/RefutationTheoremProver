/*
 * ConstantTerm.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 16 de Junho de 2007, 21:34
 */

package refutationproof.abstractsyntaxtree;

/**
 * A Constant term for the parsing
 * @author Daniel
 */
public class ConstantTerm extends Term {
    
    protected String valor;
    
    /** Creates a new instance of TermoConstante */
    public ConstantTerm(String AsValor) {
        valor = AsValor;
    }
    
    public String toString()
    {
        return valor;
    }
    
    public String recuperaValor()
    {
        return valor;
    }
    
    public Term clone()
    {
        return new ConstantTerm(valor);
    }
    
     public boolean equals(Term termo)
    {
        if(termo instanceof ConstantTerm)
        {
            return valor.equals(((ConstantTerm)termo).valor);
        }
        return false;
    }
    
}
