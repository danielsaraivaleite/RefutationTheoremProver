/*
 * Clause.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 16 de Junho de 2007, 21:26
 */

package refutationproof.abstractsyntaxtree;
import refutationproof.parsing.ClauseParser;
import refutationproof.VariableInstancesTable;
import refutationproof.Unification;
import java.util.*;

/**
 * Implements the main object for treating a clause
 * @author Daniel
 */
public class Clause {
    
    protected Vector<Expression> expressions;
    public static Clause clausulaNula = new Clause(null);
    
    /** Creates a new instance of Clausula */
    public Clause(Vector<Expression> expressoes) {
        this.expressions = expressoes;
    }
    
    public void load(String expressao)
    {
        ClauseParser parser = new ClauseParser();
        Clause c = parser.generateClause(expressao);
        expressions = c.getExpressionsCollection();
    }
    
    public static Clause parse(String expressao)
    {
        ClauseParser parser = new ClauseParser();
        return parser.generateClause(expressao);        
    }
    
    /** Creates a new instance of Clause */
    public Clause clone()
    {
        Clause c = new Clause(new Vector<Expression>());
        for(int i = 0; i < expressions.size(); i++)
        {
            c.addExpression(expressions.elementAt(i).clone());
        }
        return c;
    }
    
    public boolean equals(Clause c)
    {
        for(int i = 0; i<expressions.size(); i++)
        {
            if(! (expressions.elementAt(i).equals(c.expressions.elementAt(i))))
            {
                return false;
            }
        }
        return true;
    }    
    
    public void addExpression(Expression exp)
    {
        if(expressions == null)
            expressions = new Vector<Expression>();
        expressions.add(exp);
    }
    
    
    public int getExpressionsCount()
    {
         if(expressions == null)
             return 0;
         else
             return expressions.size();
    }
    
    
    public Vector<Expression> getExpressionsCollection()
    {
        return expressions;   
    }
    
    public void applyIdempotence()
    {
        Vector<Expression> vetorOriginal = (Vector<Expression>)expressions.clone();
        expressions.clear();
        for(int i = 0; i < vetorOriginal.size(); i++)
        {
            Expression nova = vetorOriginal.elementAt(i);
            boolean encontrou = false;
            int j = 0;
            // procura no vetor nao duplicado
            while(!encontrou && j < expressions.size())
            {
                if(expressions.elementAt(j).equals(nova))
                {
                    encontrou = true;
                }
                j++;
            }
            if(!encontrou)
            {
                expressions.add(nova);
            }
        }
    }
    
    public Clause factorize(VariableInstancesTable tabela)
    {
        Clause fator = this.clone();
        fator.instanciarVariaveis((tabela));
        fator.applyIdempotence();
        
        Vector<Expression> fatorados = new Vector<Expression>();
        
        while(factorize_aux(fator, tabela))
        {
            
        }
        
        fator.instanciarVariaveis(tabela);
        
        return fator;
    }
    
    private boolean factorize_aux(Clause fator, VariableInstancesTable tabela)
    {
        for(int i = 0; i < fator.getExpressionsCount(); i++)
        {
            for(int j = i + 1; j < fator.getExpressionsCount(); j++)
            {
                if(fator.getExpressionsCollection().get(i).negada == 
                        fator.getExpressionsCollection().get(j).negated())
                {
                    if(Unification.unify(fator.getExpressionsCollection().get(i) ,
                            fator.getExpressionsCollection().get(j), tabela ))
                    {
                        fator.getExpressionsCollection().remove(j);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public Clause instanciarVariaveis(VariableInstancesTable tabela)
    {
        if(expressions != null)
        {
            Iterator<Expression> it = expressions.iterator();
            while(it.hasNext())
            {
                Expression expr = it.next();
                Vector<Term> termos = expr.recuperaTermos();
                for(int i = 0; i < termos.size(); i++)
                {
                    if(termos.elementAt(i) instanceof VariableTerm)
                    {
                        VariableTerm tvar = (VariableTerm)termos.elementAt(i);
                        // liga a um atomo 
                        if(tabela.checkInstanceVariableAtom(tvar.getName()))
                        {
                            termos.set(i, tabela.getTermLinkedToVariable(tvar.getName()));
                        }
                        else // apenas rename
                        {
                            tvar.rename(tabela.getMostGeneralVariableName(tvar.nome));
                        }
                    }
                }
            }
        }
        return this;
    }    
    
    public String toString()
    {
        if(expressions == null || expressions.size() == 0)
        {
            return "nil";
        }
        else
        {
            StringBuffer sbBuffer = new StringBuffer();
            if(expressions != null)
            {
                for(int i = 0; i < expressions.size(); i++)
                {
                    if(sbBuffer.length() > 0)
                    {
                        sbBuffer.append(" v " );                    
                    }
                    sbBuffer.append(expressions.elementAt(i).toString());
                }            
            }
            return sbBuffer.toString();             
        }
    }    
    
    
    
}
    