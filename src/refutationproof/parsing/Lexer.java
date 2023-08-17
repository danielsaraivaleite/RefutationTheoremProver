/*
 * LexerClausal.java
 * Part of Refutation Tree Project
 * Author Daniel S Leite
 * Created on 17 de Junho de 2007, 12:51
 */

package refutationproof.parsing;

/**
 * The lexical interpretator
 * @author Daniel
 */
public class Lexer {
    
    /* Tipos e Tokens */
    public static int RELATION_NAME = 0;
    public static int VARIABLE_NAME = 1;
    public static int NEGATION = 2;
    public static int DISJUNCTION = 3;
    public static int LEFT_PARENTHESIS = 4;
    public static int RIGHT_PARENTHESIS = 5;
    public static int COMMA = 7;
    public static int NUMBER = 8;
    public static int EOF = 9;    
    
    char[] tokens;
    int tokenPos;
    StringBuffer texto = new StringBuffer();
    int currentToken;
    
    /** Creates a new instance of LexerClausal */
    public Lexer(String expressao) {
        tokens = (expressao + '\0').toCharArray();
        tokenPos = 0;
    }
    
    public int nextToken()
    {
        while(tokens[tokenPos] != '\0' && (tokens[tokenPos] == '\n' || Character.isWhitespace(tokens[tokenPos] )))
        {
            tokenPos++;
        }
        if(tokens[tokenPos] == '\0') // fim do arquivo
        {
            return currentToken = EOF;
        }
        if(Character.isLetter(tokens[tokenPos]) && Character.isUpperCase(tokens[tokenPos]) ) // variavel
        {
            texto.setLength(0);
            texto.append(tokens[tokenPos]);
            tokenPos++;
            while(Character.isLetterOrDigit(tokens[tokenPos]))
            {
                texto.append(tokens[tokenPos]);
                tokenPos++;                        
            }
            return currentToken = VARIABLE_NAME;
        }
        if(tokens[tokenPos] != 'v' && Character.isLetter(tokens[tokenPos]) && Character.isLowerCase(tokens[tokenPos]) ) // nome de relaçao
        {
            texto.setLength(0);
            texto.append(tokens[tokenPos]);
            tokenPos++;
            while(Character.isLetterOrDigit(tokens[tokenPos]))
            {
                texto.append(tokens[tokenPos]);
                tokenPos++;                        
            }
            return currentToken = RELATION_NAME;
        }
        if(Character.isDigit(tokens[tokenPos])) // numero
        {
            texto.setLength(0);
            texto.append(tokens[tokenPos]);
            tokenPos++;
            while(Character.isDigit(tokens[tokenPos]))
            {
                texto.append(tokens[tokenPos]);
                tokenPos++;                        
            }
            return currentToken = NUMBER;
        }        
        if(tokens[tokenPos] == '(')
        {
            tokenPos++;
            return currentToken = LEFT_PARENTHESIS;
        }
        if(tokens[tokenPos] == ')')
        {
            tokenPos++;
            return currentToken = RIGHT_PARENTHESIS;
        }
        if(tokens[tokenPos] == '~')
        {
            tokenPos++;
            return currentToken = NEGATION;
        }        
        if(tokens[tokenPos] == 'v' || tokens[tokenPos] == '|')
        {
            tokenPos++;
            return currentToken = DISJUNCTION;
        }
        if(tokens[tokenPos] == ',')
        {
            tokenPos++;
            return currentToken = COMMA;
        }        
        throw new RuntimeException("Invalid symbol encountered analysing: \n" + String.copyValueOf(tokens)) ;
    }
    
    public String getText()
    {
        return texto.toString();
    }
    
    public int currentToken()
    {
        return currentToken;
    }
    
}
