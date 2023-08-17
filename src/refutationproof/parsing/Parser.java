/*
 * Parser.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 16 de Junho de 2007, 21:34
 */

package refutationproof.parsing;

import refutationproof.abstractsyntaxtree.Clause;

/**
 * The parser of clauses
 * @author Daniel
 */
public class Parser {
    
    protected ClauseParser clauseParser = new ClauseParser();
    
    /** Creates a new instance of Parser */
    public Parser() {
    }
    
    public Clause generateClause(String expressao)
    {
        return clauseParser.generateClause(expressao);   
    }    
    
}
