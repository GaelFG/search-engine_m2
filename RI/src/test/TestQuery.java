package test;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.*;
import java.util.*;
import java.sql.SQLException;

import search.TermQ;
import search.TermQuery;
import store.BaseReader;

/**
 * Classe de test pour les requetes La requete peut etre composee de plusieurs mots cles separes par des espaces
 * Utilisation : java test/TestQuery fichierconfig "terme1 terme2 terme3" fichiersortie
 *
 */
public class TestQuery {

	public static void main(String argv[])  {
		int seuil = 50;
		try  {
			FileWriter out= new FileWriter(new File(argv[2]));
			String monfichier= new String(argv[0]);
			System.out.println(monfichier);
			String q= new String (argv[1]);
			TermQuery query = new TermQuery(q);
			BufferedReader config = new BufferedReader (new FileReader(monfichier));
			System.out.println(monfichier);
			String ConnectURL;
			String login="";
			String pass="";
			ConnectURL=config.readLine();

			if (ConnectURL!=null) {
				login=config.readLine();
			}
			if (login!=null) {
				pass=config.readLine();
			}
			config.close();
			BaseReader base=new BaseReader(ConnectURL,login,pass);
			// recherche de tous les documents pertinents de l'index et on calcule le score de pertinence
			TreeMap results = query.score(base);
			System.out.println(results.size() +" resultats");
			// on trie la TreeMap de resultats selon les valeurs,
			// cad selon le score (et non selon la cle, cad
			// selon l'identifiant du doc )
			List cles = new ArrayList(results.keySet());
			Collections.sort(cles, new CompScore(results));

			for (Iterator it=cles.listIterator();it.hasNext();) {
				// on recupere l'id du document et on va chercher son nom
				double tfidf_total = 0;
				Integer docid = (Integer) it.next();
				String nom_fichier= base.document(docid).name;
				//out.write(nom_fichier+"\t"+results.get(docid)+"\n");
				//out.write(node.getTextContent()+"\n\n");	
				
				//calcul de tf, idf et idtf
				for(TermQ term : query.terms) {
					int poids = base.getPoidsTerm(docid, term.text);
					int nbtotal = base.getDocTermCount(docid);
					double tf = (float) poids/nbtotal;
					double idf = (float)(Math.log((float)138/results.size()));
					double tfidf = tf*idf;
					tfidf_total = tfidf_total + tfidf;					
				}
				out.write(nom_fichier+"\t"+tfidf_total+"\n");
				//double tf = base.getPoidsTerm(docid, terme_id)
				//double idf = Math.log(138/results.size());
				//double tfidf = (Float)results.get(docid)*idf;
				//out.write(nom_fichier+"\t"+tfidf+"\n");
			}
			base.close();
			out.close();
		}
		catch (IOException e) { System.out.println("Problem : End of file."+e.getMessage());}
		catch (SQLException e2) {System.out.println("SQL Error ."+e2.getMessage());}
		catch (Exception e) {System.out.println("Error : Unable to process files. "+e.getMessage());}  
	}


	/**
	 *  Classe permettant de trier les resultats sur les scores et non sur les identifiants de noeuds
	 */
	public static class CompScore implements Comparator {
		private Map copieresults;

		public CompScore(Map results) {
			this.copieresults= results;
		}

		public int compare (Object o1, Object o2) {
			Float s1= (Float) copieresults.get((Integer)o1);
			Float s2= (Float) copieresults.get((Integer) o2);
			return (s2.compareTo(s1));
		}
	}
}