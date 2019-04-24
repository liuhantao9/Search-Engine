package search.analyzers;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.DoubleLinkedList;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.exceptions.NotYetImplementedException;
import search.models.Webpage;

import java.net.URI;

/**
 * This class is responsible for computing how "relevant" any given document is
 * to a given search query.
 *
 * See the spec for more details.
 */
public class TfIdfAnalyzer {
    // This field must contain the IDF score for every single word in all
    // the documents.
    private IDictionary<String, Double> idfScores;

    // This field must contain the TF-IDF vector for each webpage you were given
    // in the constructor.
    //
    // We will use each webpage's page URI as a unique key.
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;

    // Feel free to add extra fields and helper methods.
    //private IDictionary<URI, IDictionary<String, Double>> allCounts;
    private IDictionary<URI, IDictionary<String, Double>> tfScores;
    private IDictionary<URI, Double> documentNorm;


    public TfIdfAnalyzer(ISet<Webpage> webpages) {
        // Implementation note: We have commented these method calls out so your
        // search engine doesn't immediately crash when you try running it for the
        // first time.
        //
        // You should uncomment these lines when you're ready to begin working
        // on this class.
        this.computetTfIdfScores(webpages);
        this.documentNorm = new ChainedHashDictionary<URI, Double>();
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
    }

    // Note: this method, strictly speaking, doesn't need to exist. However,
    // we've included it so we can add some unit tests to help verify that your
    // constructor correctly initializes your fields.
    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }

    // Note: these private methods are suggestions or hints on how to structure your
    // code. However, since they're private, you're not obligated to implement exactly
    // these methods: Feel free to change or modify these methods if you want. The
    // important thing is that your 'computeRelevance' method ultimately returns the
    // correct answer in an efficient manner.

    /**
     * This method should return a dictionary mapping every single unique word found
     * in any documents to their IDF score.
     */
    private void computetTfIdfScores(ISet<Webpage> pages) {
        //throw new NotYetImplementedException();
    	this.tfScores = new ChainedHashDictionary<URI, IDictionary<String, Double>>();
        this.idfScores = new ChainedHashDictionary<String, Double>();
        int pageSize = pages.size();
    	for (Webpage page:pages) {
    		IDictionary<String, Double> tempTf = new ChainedHashDictionary<String, Double>();
    		ISet<String> visited = new ChainedHashSet<String>();
    		URI uri = page.getUri();
    		int wordSize = page.getWords().size();
    		for (String word:page.getWords()) {
    			if (tempTf.containsKey(word)) {
    				tempTf.put(word, tempTf.get(word) + 1.0/wordSize);
    			} else {
    				tempTf.put(word, 1.0/wordSize);
    			}
    			if (!visited.contains(word)) {
    				visited.add(word);
    				if (this.idfScores.containsKey(word)) {
    					this.idfScores.put(word, Math.log((double) pageSize/(pageSize/Math.exp(this.idfScores.get(word)) + 1.0)));
    				} else {
    					this.idfScores.put(word, Math.log((double) pageSize/1.0));
    				}
    			}
    		}
    		this.tfScores.put(uri, tempTf);
    	}
    }

    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) score.
     *
     * We are treating the list of words as if it were a document.
     *//*
    private IDictionary<String, Double> computeTfScores(ISet<Webpage> pages, IList<String> words) {
        //throw new NotYetImplementedException();
    	IDictionary<String, Double> scoreCounts = new ArrayDictionary<String, Double>();
    	for (String word:words) {
    		if (scoreCounts.containsKey(word)) {
    			scoreCounts.put(word, scoreCounts.get(word) + 1.0/words.size());
    		} else {
    			scoreCounts.put(word, 1.0/words.size());
    		}
    	}
    	return scoreCounts;
    }*/

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
        // Hint: this method should use the idfScores field and
        // call the computeTfScores(...) method.
        //throw new NotYetImplementedException();
    	IDictionary<URI, IDictionary<String, Double>> vectors = new ChainedHashDictionary<URI, IDictionary<String, Double>>();
    	for (KVPair<URI, IDictionary<String, Double>> dict:this.tfScores) {
    		IDictionary<String, Double> tempVector = new ChainedHashDictionary<String, Double>();
    		URI uri =  dict.getKey();
    		for (KVPair<String, Double> pair:dict.getValue()) {
    			double score = 0.0;
    			if (this.idfScores.containsKey(pair.getKey())) {
    				score += pair.getValue()*this.idfScores.get(pair.getKey());
    			}
    			tempVector.put(pair.getKey(), score);
    			if (!this.documentNorm.containsKey(uri)) {
        			this.documentNorm.put(uri, Math.sqrt(score*score));
    			} else {
    				this.documentNorm.put(uri, Math.sqrt(this.documentNorm.get(uri)*this.documentNorm.get(uri) + score*score));
    			}
    		}
    		vectors.put(uri, tempVector);
    	}
    	return vectors;
    }

    /**
     * Returns the cosine similarity between the TF-IDF vector for the given query and the
     * URI's document.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public Double computeRelevance(IList<String> query, URI pageUri) {
        // TODO: Replace this with actual, working code.

        // TODO: The pseudocode we gave you is not very efficient. When implementing,
        // this smethod, you should:
        //
        // 1. Figure out what information can be precomputed in your constructor.
        //    Add a third field containing that information.
        //
        // 2. See if you can combine or merge one or more loops.
        //return 0.0;
    	IDictionary<String, Double> documentVector =  this.documentTfIdfVectors.get(pageUri);
    	IDictionary<String, Double> queryVector = this.computeQuery(query);
    	double documentVectorNorm = this.documentNorm.get(pageUri);
    	double queryVectorNorm = 0.0;
    	
    	double numerator = 0.0;
    	for (String word:query) {
    		double docWordScore;
    		if (documentVector.containsKey(word)) {
    			docWordScore = documentVector.get(word);
    		} else {
    			docWordScore = 0.0;
    		}
    		double queryWordScore = queryVector.get(word);
    		numerator += docWordScore * queryWordScore;
    		queryVectorNorm += queryWordScore*queryWordScore;
    	}
    	queryVectorNorm = Math.sqrt(queryVectorNorm);

    	double denominator = documentVectorNorm * queryVectorNorm;

    	if (denominator != 0) {
    		return numerator / denominator;
    	} else {
    		return 0.0;
    	}
    }
    
    /*
    public Double computeRelevance(IList<String> query, URI pageUri) {
        // TODO: Replace this with actual, working code.

        // TODO: The pseudocode we gave you is not very efficient. When implementing,
        // this smethod, you should:
        //
        // 1. Figure out what information can be precomputed in your constructor.
        //    Add a third field containing that information.
        //
        // 2. See if you can combine or merge one or more loops.
        //return 0.0;
    	IDictionary<String, Double> documentVector =  this.documentTfIdfVectors.get(pageUri);
    	IDictionary<String, Double> queryVector = this.computeQuery(query);
    	
    	double numerator = 0.0;
    	for (String word:query) {
    		double docWordScore;
    		if (documentVector.containsKey(word)) {
    			docWordScore = documentVector.get(word);
    		} else {
    			docWordScore = 0.0;
    		}
    		double queryWordScore = queryVector.get(word);
    		numerator += docWordScore * queryWordScore;
    	}

    	double denominator = norm(documentVector) * norm(queryVector);

    	if (denominator != 0) {
    		return numerator / denominator;
    	} else {
    		return 0.0;
    	}
    }
    
    private Double norm(IDictionary<String, Double> vector) {
    	double output = 0.0;
    	for (KVPair<String, Double> pair:vector) {
    		double score = pair.getValue();
    		output += score*score;
    	}
    	return Math.sqrt(output);
    }*/
    
    private IDictionary<String, Double> computeQuery(IList<String> query) {
    	IDictionary<String, Double> vector = new ChainedHashDictionary<String, Double>();
    	for (String word:query) {
    		if (vector.containsKey(word) && this.idfScores.containsKey(word)) {
    			vector.put(word, vector.get(word) + this.idfScores.get(word)/query.size());
    		} else if (this.idfScores.containsKey(word)) {
    			vector.put(word, this.idfScores.get(word)/query.size());
    		} else {
    			vector.put(word, 0.0);
    		}
    	}
    	return vector;
    }
    }    		