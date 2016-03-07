package utils;

/** Classe permettant le calcul de stocres
 */
public final class Similarity {
	private Similarity() {}			  // no public constructor

	/**
	 * This method either compute the inverse element frequency or inverse document frequency for a given term.
	 * @param term the term for which the computation is made
	 * @param reader BaseReader allowing the indx to be readed
	 */
	// public static final float inverseFrequency(Term term, BaseReader reader) throws IOException, SQLException {
	// Simple ief (Inverse Element Frequency) computation
	// return ief(term.element_count, reader.maxLeaf());
	// Simple idf (Inverse Document Frequency) computation
	//System.out.println(term.doc_count+"   "+reader.maxDoc());
	//return idf(term.doc_count, reader.maxDoc());
	//} // inverseFrequency()

	/**
	 * Computes Inverse Element Frequency
	 **/
	public static final float ief(int eltFreq, int numElts) {
		return (float)(Math.log(numElts/(double)(eltFreq+1)) + 1.0);
	}

	/**
	 * Computes Inverse Document Frequency
	 **/
	public static final float idf(int docFreq, int numDocs) {
		return (float)(Math.log(numDocs/(double)(docFreq+1))+1.0);

	}

	/**
	 * Computes the Innerproduct for a TERM find in a doc and in a query
	 */
	public static final float InnerProd(float poidDoc, short poidReq) {
		return (float) (poidDoc*poidReq);
	} 
}