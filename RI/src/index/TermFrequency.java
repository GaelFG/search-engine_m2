package index;

/** 
 * Un objet TermFrequency contient pour un terme donn� le document dans lequel il apparait et sa fr�quence
 * */
public final class TermFrequency {
	/** id du document*/
	public int doc_id;
	/** fr�quence dans le document*/
	public short frequency;

	/** Defaults Constructor */
	public TermFrequency() {
	}

	/** 
	 * Construit un objet TermFrequency
	 * @param doc_id l'id du noeud
	 * @param frequency fr�quence du terme dans le noeud
	 **/
	public TermFrequency(int doc_id, short  frequency) {
		this.doc_id=doc_id;
		this.frequency =frequency;
	}

	/**
	 * Prints a TermFrequency object
	 */
	public void PrintTermFrequency() {
		System.out.print(" Frequency :");
		System.out.print(doc_id+"\t"+frequency+"\t");
		System.out.print(" - ");                  
	}

}