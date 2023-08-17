/*
 * ClauseParser.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 16 de Junho de 2007, 21:27
 */

package refutationproof.parsing;

import refutationproof.abstractsyntaxtree.Clause;
import refutationproof.abstractsyntaxtree.VariableTerm;
import refutationproof.abstractsyntaxtree.ConstantTerm;
import refutationproof.abstractsyntaxtree.Term;
import refutationproof.abstractsyntaxtree.Expression;
import java.util.*;

/**
 * A Clause PArser
 * @author Daniel
 */
public class ClauseParser {   
    
    protected Lexer lexer;
    
    /** Creates a new instance of ParserClausal */
    public ClauseParser() {    
    }
    
    public Clause generateClause(String expression)
    {
        lexer = new Lexer(expression);
        Clause clausula = new Clause(new Vector<Expression>());        
        do
        {
            lexer.nextToken();
            clausula.addExpression(analyseExpression());            
        }
        while(lexer.currentToken() == lexer.DISJUNCTION);
        if(lexer.currentToken() != lexer.EOF)
        {
            throw new RuntimeException("Parsing error. EOF expected.");
        }
        return clausula;
    }
    
    protected Expression analyseExpression()
    {
        boolean negada = false;
        String nome;
        if(lexer.currentToken() == lexer.NEGATION)
        {
            negada = true;
            lexer.nextToken();
        }
        if(lexer.currentToken() != lexer.RELATION_NAME)
        {
            throw new RuntimeException("Parsing error. Relation name expected");
        }
        nome = lexer.getText();
        Vector<Term> termos = null;
        lexer.nextToken();
        if(lexer.currentToken() == lexer.LEFT_PARENTHESIS)
        {            
            termos = analyseTerms();
            if(lexer.currentToken() != lexer.RIGHT_PARENTHESIS)
            {
              throw new RuntimeException("Parsing error. \')\' missing.");
            }
            lexer.nextToken();
        }
        if(termos == null) termos = new Vector<Term>();
        return new Expression(nome, termos.size(), termos, negada);
    }
    
    protected Vector<Term> analyseTerms()
    {
        Vector<Term> termos = new Vector<Term>();
        do
        {
            lexer.nextToken();
            Term termo;
            if(lexer.currentToken() == lexer.NUMBER || lexer.currentToken() == lexer.RELATION_NAME)
            {
                termo = new ConstantTerm(lexer.getText());
            }
            else
            {
                if(lexer.currentToken() == lexer.VARIABLE_NAME)
                {
                    termo = new VariableTerm(lexer.getText());
                }
                else
                {
                    throw new RuntimeException("Parsing eror. Variable or atom expected as terms in relation");
                }
            }
            lexer.nextToken();
            termos.add(termo);            
        } 
        while(lexer.currentToken() == lexer.COMMA);
        return termos;        
    }    
}
