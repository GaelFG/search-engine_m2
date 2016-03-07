package search;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Iterator;
import index.Term;
import index.TermFrequency;
import store.BaseReader;
import utils.Similarity;

/**
 *  Classe permettant de traiter les requetes : recherche des termes dans l'index, calcul des scores des elements
 */
final public class TermQuery {
	/** vecteur contenant les termes de la requetes (objets Term) */
	private Vector<TermQ> terms; 

	/**
	 * Constructeur : construit le vecteur des termes de la requete
	 */
	public TermQuery(String query){  
		terms=new Vector<TermQ>();
		System.out.println("La requete est:"+query);
		String[] termstable = query.split(" ");
		//System.out.println(termstable.length);
		// on pourrait lemmatiser mais on ne le fait pas!
		for (int i=0;i<termstable.length;i++) {
			String[] termpoid = termstable[i].split(":");
			TermQ monTerme = null;
			Short un = 1;
			if (termpoid.length <2) {
				monTerme = new TermQ (termpoid[0].toLowerCase(),un);
			}
			else {
				monTerme = new TermQ (termpoid[0].toLowerCase(),Short.parseShort(termpoid[1]));
			}
			terms.add(monTerme);
		}

		System.out.println("Fin de la requete");
	}

	/**
	 * Calcule les scores des documents contenant au moins un terme de la requete
	 */
	public TreeMap score(BaseReader reader) throws IOException {
		TreeMap result = new TreeMap();
		// pour chaque terme de la requete   
		for (Enumeration e=terms.elements(); e.hasMoreElements();) {

			TermQ myTermQuery = null;
			Term myTerm = null;
			try { 
				myTermQuery = (TermQ) e.nextElement();
				System.out.println("recherche dans l'index des doc pour "+myTermQuery.text);
				myTerm = reader.readTerm(myTermQuery.text);   // on recupère toutes les infos stockées sur ce terme dans l'index           
				if (myTerm!=null) {
					// on pourrait calculer la frequence inverse du terme !!  
					// pour chaque document contenant le terme, on calcule un score
					for (Iterator it=myTerm.frequency.keySet().iterator();it.hasNext();) {
						TermFrequency mafrequence = (TermFrequency) myTerm.frequency.get(it.next());
						float weights = Similarity.InnerProd(mafrequence.frequency,	myTermQuery.weigth);
						// si ce document a deja un score, on additionne
						if (!result.containsKey(new Integer(mafrequence.doc_id))) {
							result.put(new Integer(mafrequence.doc_id), new Float(weights));
						} else { // sinon on insere le document
							Float ancienscore = (Float) result.get(new Integer(mafrequence.doc_id));
							result.remove(new Integer(mafrequence.doc_id));
							result.put(new Integer(mafrequence.doc_id),ancienscore+new Float(weights));
						}
					}
				}   
			}
			catch (SQLException ex) { 
				System.out.println("Erreur de recuperation du terme ou de calcul du poids");
				System.out.println("SQLException: " + ex.getMessage()); 
				System.out.println("SQLState: " + ex.getSQLState()); 
				System.out.println("VendorError: " + ex.getErrorCode()); 
			}
		}
		return result;
	}
}