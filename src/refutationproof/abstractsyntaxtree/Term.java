/*
 * Term.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 16 de Junho de 2007, 21:33
 */

package refutationproof.abstractsyntaxtree;

/**
 * A Term object of the parser
 * @author Daniel
 */
public abstract class Term {
    
    /** Creates a new instance of Term */
    public abstract Term clone();
    
    public abstract boolean equals(Term termo);
    
}
