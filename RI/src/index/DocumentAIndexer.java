package index;

/** 
 * Les objets DocumentAIndexer contiennnent les unit�s d'indexation
 * Un DocumentAIndexer est défini par son nom, 
 * et un vecteur de termes  - <i>ContentVector</i>-.
 * Il est aussi decrit par le nombre de termes, 
 * Les objets DocumentAIndexer sont crees pendant la phase d'indexation.
 * */
public final class DocumentAIndexer { 
	/** Identifiant du document*/
	public int id;  
	/** Nom du document*/
	public String name=null;

	/** Constuit un nouveau document. */
	public DocumentAIndexer() { }

	/** Construit un nouveau document � indexer
	 * @param identifiant identifiant de document
	 * @param name nom du document
	 **/
	public DocumentAIndexer(int identifiant, String name) {
		this.id = identifiant;
		this.name=name;
	}

	/** Affiche le document */
	public void PrintDocument() {
		System.out.println("Id :"+this.id+"\tDocument :"+this.name);       
	} 
}