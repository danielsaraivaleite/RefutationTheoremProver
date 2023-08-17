/*
 * Resolution.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 17 de Junho de 2007, 13:18
 */

package refutationproof;
import refutationproof.abstractsyntaxtree.Clause;
import refutationproof.abstractsyntaxtree.Expression;

/**
 * Implement the resolution principle
 * @author Daniel
 */
public class Resolution {
    
     boolean bSucesso = false;     
     Clause resolvente;
     VariableInstancesTable tabela;
     int PosA, PosB;

    
    /** Creates a new instance of Resolucao */
    public Resolution()
    {
        
    }
        
    public void applyResolution(Clause clausula1, Clause clausula2, VariableInstancesTable tabela)
    {
        this.tabela = tabela.clone();
        
        resolvente = null;
        bSucesso = false;
        
        Clause c1 = clausula1.clone();
        Clause c2 = clausula2.clone();
        
        // Factorize
        c1.instanciarVariaveis(tabela);
        c2.instanciarVariaveis(tabela);
        
        c1 = c1.factorize(tabela);
        c2 = c2.factorize(tabela);
        
        c1.applyIdempotence();
        c2.applyIdempotence();
        
        // bin resoltion
        if(applyBinaryResolution(c1, c2))
        {
            c1.getExpressionsCollection().remove(PosA);
            c2.getExpressionsCollection().remove(PosB);
            
            if(Unification.unify(c1, c1.clone().instanciarVariaveis(tabela), tabela) && 
                    Unification.unify(c2, c2.clone().instanciarVariaveis(tabela), tabela))
            {
                bSucesso = true;
                // determina o resolvente
                Clause cresolv = c1.instanciarVariaveis(tabela);
                c2.instanciarVariaveis(tabela);
                cresolv.getExpressionsCollection().addAll(c2.getExpressionsCollection());
                cresolv.applyIdempotence();
                resolvente = cresolv;
                if(cresolv.getExpressionsCount() == 0)
                {
                    this.resolvente = Clause.clausulaNula;
                }
            }
        }
    }
    
    private boolean applyBinaryResolution(Clause clausula1, Clause clausula2)
    {
        PosA = PosB = 0;
        for(int i = 0; i < clausula1.getExpressionsCount(); i++)
        {
            for(int j = 0; j < clausula2.getExpressionsCount(); j++)
            {
                Expression exp1 = clausula1.getExpressionsCollection().get(i);
                Expression exp2 = clausula2.getExpressionsCollection().get(j);
                if(exp1.negated() != exp2.negated())
                {
                    if(Unification.unify(exp1, exp2, tabela))
                    {
                        PosA = i;
                        PosB = j;
                        return true;
                    }
                }
            }
        }
        return false;        
    }
        
    
    public boolean success()
    {
        return bSucesso;
    }
    

    public Clause recuperaResolvente()
    {
        return resolvente;
    }
    
    public boolean gotNil()
    {
        return resolvente == Clause.clausulaNula;
    }
    
    public VariableInstancesTable getVariableInstancesTable()
    {
        return tabela;
    }  
}
