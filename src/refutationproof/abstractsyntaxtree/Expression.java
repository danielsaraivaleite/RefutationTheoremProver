/*
 * Expression.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 16 de Junho de 2007, 21:26
 */

package refutationproof.abstractsyntaxtree;
import java.util.*;

/**
 * A general expression for the parser
 * @author Daniel
 */
public class Expression {
    
    protected boolean negada = false;
    protected int aridade = 0;
    protected Vector<Term> termos;
    protected String nome = "";
    
    /** Creates a new instance of Expressao */
    public Expression(String AsNome, int AiAridade, Vector<Term> AvTermos, boolean AbNegada ) {
        nome = AsNome;
        aridade = AiAridade;
        termos = AvTermos;
        negada = AbNegada;
    }
    
    public Expression clone()
    {
        Vector<Term> termosExp = new Vector<Term>();
        for(int i = 0; i < termos.size(); i++)
        {
            termosExp.add(termos.elementAt(i).clone());
        }        
        return new Expression(nome, aridade, termosExp, negada);
    }
    
    public boolean equals(Expression exp)
    {
        boolean igualRelacao;
        igualRelacao = (aridade == exp.aridade) && (negada == exp.negada) &&
                nome.equals(exp.nome);
        if(igualRelacao)
        {
            for(int i = 0; i < termos.size(); i++)
            {
                if(!(termos.elementAt(i).equals(exp.termos.elementAt(i))))
                {
                    return false;
                }
            }
        }
        return igualRelacao;        
    }
    
    public boolean negated()
    {
        return negada;
    }
    
    public int recuperaAridade()
    {
        return aridade;        
    }
    
    public String recuperaNome()
    {
        return nome;        
    }
    
    public Vector<Term> recuperaTermos()
    {
        return termos;   
    }
    
    public String toString()
    {
        StringBuffer saida = new StringBuffer();
        if(negada)
        {
            saida.append("~");            
        }
        saida.append(nome);
        if(aridade > 0)
        {
            saida.append("(");            
            for(int i = 0; i < termos.size(); i++)
            {
                saida.append(termos.elementAt(i).toString());
                if(i != termos.size() -1)
                {
                    saida.append(" , ");
                }
            }
            saida.append(")");            
        }
        return saida.toString();
    }
       
       
    
}
    

