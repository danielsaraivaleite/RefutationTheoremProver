/*
 * Unification.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 17 de Junho de 2007, 17:04
 */

package refutationproof;
import refutationproof.abstractsyntaxtree.Clause;
import refutationproof.abstractsyntaxtree.VariableTerm;
import refutationproof.abstractsyntaxtree.ConstantTerm;
import refutationproof.abstractsyntaxtree.Term;
import refutationproof.abstractsyntaxtree.Expression;

/**
 * Implements the unification principle
 * @author Daniel
 */
public class Unification {
    
   
    public static boolean unify(Expression expressao1, Expression expressao2, 
            VariableInstancesTable tabela)
    {
        VariableInstancesTable tabelaTemp = tabela.clone();
        
        // confere nome relação e aridade
        if(expressao1.recuperaNome().equals(expressao2.recuperaNome()) && 
                expressao1.recuperaAridade() == expressao2.recuperaAridade())
        {
            // check terms unification
            for(int i = 0; i < expressao1.recuperaAridade(); i++)
            {
                Term termo1 = expressao1.recuperaTermos().elementAt(i);
                Term termo2 = expressao2.recuperaTermos().elementAt(i);
                // atom and atom
                if(termo1 instanceof ConstantTerm && 
                        termo2 instanceof ConstantTerm)
                {
                    ConstantTerm atomo1 = (ConstantTerm)termo1;
                    ConstantTerm atomo2 = (ConstantTerm)termo2;
                    if(!atomo1.recuperaValor().equals(atomo2.recuperaValor()))
                    {
                        return false;
                    }
                }
                else // at least one variable involved                  
                {
                    if(termo1 instanceof VariableTerm)
                    {
                        if(!tabelaTemp.instantiate(((VariableTerm)termo1).getName(), termo2))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        if(!tabelaTemp.instantiate(((VariableTerm)termo2).getName(), termo1))
                        {
                            return false;
                        }                        
                    }
                }                
            }
            // Unifica as tabelas
            tabela.assign(tabelaTemp);
            return true;
        }       
        return false;
    }
    
    public static boolean unify(Clause c1, Clause c2, VariableInstancesTable tabela)
    {
        Clause cPrimeira = c1.clone();
        Clause cSegunda = c2.clone();
        
        cPrimeira = cPrimeira.instanciarVariaveis(tabela);
        cSegunda = cSegunda.instanciarVariaveis(tabela);
        
        cPrimeira.applyIdempotence();
        cSegunda.applyIdempotence();
        
        if(cPrimeira.getExpressionsCount() == cSegunda.getExpressionsCount())
        {
            for(int i = 0; i < cPrimeira.getExpressionsCollection().size(); i++)
            {
                if(cPrimeira.getExpressionsCollection().get(i).negated() !=
                        cSegunda.getExpressionsCollection().get(i).negated())
                {
                    return false;
                }
                if(!unify(cPrimeira.getExpressionsCollection().get(i), 
                        cSegunda.getExpressionsCollection().get(i), tabela))
                {
                    return false;                                        
                }
            }
            return true;
        }
        else
        {
            return false;
        }  
        
    }

    
}
