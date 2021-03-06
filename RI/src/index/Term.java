package index;


import java.util.Iterator;
import java.util.TreeMap;

/** 
 * Les objets Term contiennent pour un identifiant donn� le texte associ� et pour chaque document, la fr�quence du terme dans le document
 * */
public final class Term {  
  /** id du terme */
  public int term_id;
  /** texte du terme */
  public String text;
  /** frequence du terme dans toute la collection*/
 // public int freq;
  /** nombre de documents contenant le terme*/
  //public int doc_count ;

  /** TreeMap contenant des objets TermFrequency
  * @see TermFrequency
  */
  public TreeMap frequency;

 /** Default constructor*/
 public Term() {
 }
 
 /** 
 * Construit un nouveau terme
 * @param text the Term text
 */
 public Term(String text) {
  this.text=text;       
 }

  /**
  * Construit un nouveau terme
  * @param id identifiant du terme
  * @param text texte du terme
  * @param frequency TreeMap contenant des objets TermFrequency
  * @see TermFrequency
  */
 // public Term(int id, String text, int freq, int doc_nb,  TreeMap frequency) {
 public Term(int id, String text,  TreeMap frequency) {
  this.term_id=id;
  this.text =text;
//  this.freq = freq;
 // this.doc_count = doc_nb;
  this.frequency = new TreeMap();
  this.frequency = frequency;
  }

  /**
  * Prints a Term object
  */
  public void PrintTerm() {
  System.out.print("Term :");
  System.out.print(term_id+"\t"+text);
  //System.out.print("\t"+this.freq+"\t"+this.doc_count+"\t "+"\t");
          for (Iterator it = frequency.keySet().iterator(); it.hasNext(); ){
                TermFrequency tempTermFrequency=new TermFrequency();
               tempTermFrequency = (TermFrequency) frequency.get(it.next());
                tempTermFrequency.PrintTermFrequency();
           }  
  System.out.println();                  
  }
 
}