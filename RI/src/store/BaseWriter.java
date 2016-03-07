
package store;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Blob;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Hashtable;


import java.io.*;

import index.*;
import utils.Converter;


/**
 * Cette classe permet de creer une BD relationnelle Postgres et de stocker des donnees dans la base
 *
 * Tables creees : Document, Termes, TermesDoc
 */



// Notice, do not import com.mysql.jdbc.* 
// or you will have problems! 

public class BaseWriter { 
     
      static Connection conn;
      
             
     
     /**
     * Constructeur. <br>
     * Effectue une connexion a la base
     * @param ConnectURL String contenant l'URL pour la connection. 
     * @param login Login pour la connection a la base
     * @param pass Mot de passe pour la collection a la base
     */
     public BaseWriter(String ConnectURL, String login, String pass) {
             
         try { 
            

            Class.forName("org.postgresql.Driver").newInstance(); 
            System.out.println("Driver charge.");
             } catch (Exception ex) { 
            // handle the error 
         } 
        
        try {
             System.out.println("Tentative de connection..."+ConnectURL+login); 
              conn = DriverManager.getConnection(ConnectURL,login,pass);
             System.out.println("Connection etablie!");  
            
        }
        catch (SQLException ex) {
            // handle any errors 
            System.out.println("SQLException: " + ex.getMessage()); 
            System.out.println("SQLState: " + ex.getSQLState()); 
            System.out.println("VendorError: " + ex.getErrorCode()); 
        }
             
     } // BaseWriter()
     
    
    /**
    * Creee toutes les tables de l'index:
    * 
    * Efface les tables si elles existent deja.
    */
    public static void create() throws SQLException{ 
           
   // assume conn is an already created JDBC connection
   Statement stmt = null; 


    stmt = conn.createStatement(); 
    boolean rs = stmt.execute("drop table if exists TermesDoc, Documents, Termes;"); 
    
    rs = stmt.execute("create table Documents (doc_id int primary key, document varchar(300)) ;"); 
	System.out.println("Table Documents creee");
	
	
	rs = stmt.execute("create table Termes (term_id int primary key, term varchar(20) NOT NULL );");
		
	rs = stmt.execute("create table TermesDoc (term_id int, doc_id int, poids real, PRIMARY KEY(term_id,doc_id));");
    System.out.println("Table Termes creees");
	
    if (stmt != null) { 
        try { 
            stmt.close(); 
        } catch (SQLException sqlEx) { // ignore
        } 

        stmt = null; 
    } 
  
   
} //create()



/**
* Insere le contenu d'un vecteur de DocumentAIndexer dans la table Documents.
*@see DocumentAIndexer
*@param myDocumentVector Vecteur d'objets DocumentAIndexer
*/
public static void insertDocument(Vector myDocumentVector) throws SQLException{
        
   
        // assume conn is an already created JDBC connection
       Statement stmt = null; 
       
       stmt = conn.createStatement(); 
       // pour chaque DocumentAIndexer du vecteur
       for (Enumeration e=myDocumentVector.elements(); e.hasMoreElements(); ) {
              
            DocumentAIndexer tempDocument=new DocumentAIndexer();
            tempDocument= (DocumentAIndexer) e.nextElement(); 
            String query = "insert into Documents (doc_id, document) values ("+tempDocument.id+",\'"+tempDocument.name+"\');";
           // System.out.println(query);
            try {    stmt.execute(query);
            }
            catch (SQLException sqlEx) { System.out.println("Erreur dans l'insertion dans Documents : "+ sqlEx.getMessage());
            } 
              
           }
        if (stmt != null) { 
        try { 
            stmt.close(); 
        } catch (SQLException sqlEx) { System.out.println("Erreur dans l'insertion dans Documents : "+ sqlEx.getMessage());
        } 

    } 
      
   
} // insertDocument()








/**
*Insere le contenu d'une hashtable de termes (objets Term) dans la table Termes et dans la table TermesDoc
* 
*@see Term
*@param myPostingTable Hashtable d'objets Term
*/
public static void insertPosting(Hashtable myPostingTable) throws SQLException{
        
  
        // assume conn is an already created JDBC connection
       Statement stmt = null; 
       PreparedStatement pstmt = null;
       stmt = conn.createStatement(); 
       // for each Term in the hashtable 
       for (Enumeration e=myPostingTable.elements(); e.hasMoreElements(); ) {
               
                Term tempTerm=new Term();
                tempTerm = (Term) e.nextElement();
                boolean rs;
				//System.out.println("j essaie :"+tempTerm.text);
                String query = "insert into Termes (term_id,term) values ("+tempTerm.term_id+",\'"+tempTerm.text+"\')";
                try {    stmt.execute(query);
                }
                catch (SQLException sqlEx) { System.out.println(query +"Erreur dans l'insertion du Term : "+ sqlEx.getMessage());
                }         
               
               
               for (Iterator it = tempTerm.frequency.keySet().iterator(); it.hasNext(); ){
                                TermFrequency tempTermFrequency=new TermFrequency();
                                tempTermFrequency = (TermFrequency) tempTerm.frequency.get(it.next()); 
                                String query2 = "insert into TermesDoc (term_id,doc_id,poids) values ("+tempTerm.term_id+","+tempTermFrequency.doc_id+","+tempTermFrequency.frequency+")";
                                rs = stmt.execute(query2);      
                				
                        }  // for it
             } //enumeration
        if (stmt != null) { 
        try { 
            stmt.close(); 
        } catch (SQLException sqlEx) { // ignore
        } 

        stmt = null; 
    } //stmt
    //System.out.println("Insertion dans la table Termes : ok");
 
        
} // insertPosting()


/**
* Ferme la connection au serveur mySQL
*/
public static void close() {
     try {
          conn.close();     
           
        } catch (SQLException ex) {
            // handle any errors 
            System.out.println("SQLException: " + ex.getMessage()); 
            System.out.println("SQLState: " + ex.getSQLState()); 
            System.out.println("VendorError: " + ex.getErrorCode()); 
        }   
} //close()



 
 
} // BaseWriter.java
