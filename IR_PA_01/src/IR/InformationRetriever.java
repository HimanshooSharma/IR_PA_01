package IR;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import IR.indexing.CustomIndexWriter;
import IR.parsing.FieldNames;

public class InformationRetriever {
	
	private Directory indexDirectory;
	private File corpusDirectory;
	private Analyzer analyzer;
	private CustomIndexWriter indexWriter;
	private IndexReader indexReader;
	private IndexSearcher indexSearcher;

	public InformationRetriever(String indexDirectory, String corpusDirectory) throws IOException {
		this.indexDirectory = FSDirectory.open(Paths.get(indexDirectory));
		this.corpusDirectory = new File(corpusDirectory);
		this.analyzer = new EnglishAnalyzer();
		
		this.indexWriter = new CustomIndexWriter(
			this.indexDirectory, 
			new IndexWriterConfig(this.analyzer)
		);
		
		this.preprocessing();
		
		this.indexReader = DirectoryReader.open(this.indexDirectory);
		this.indexSearcher = new IndexSearcher(this.indexReader);
	}
	
	public void preprocessing() throws IOException {
		System.out.println("Preprocess Documents and Create Index... ");
		this.indexWriter.createIndexFromCorpusDirectory(this.corpusDirectory);
		this.indexWriter.commit();
		System.out.println("");
	}
	
	public void retrieval(String queryStr) throws ParseException, IOException {
		Query query = new MultiFieldQueryParser(FieldNames.QUERY_FIELD_NAMES, this.analyzer).parse(queryStr);
		TopDocs hits = this.indexSearcher.search(query, Integer.MAX_VALUE);
		
		int cnt = 1;
		
		for(ScoreDoc result : hits.scoreDocs) {
			Document document = indexReader.document(result.doc);
			
			System.out.println(cnt + ". \"" + document.get(FieldNames.KEY_FILE_NAME) + "\"");
			if (document.get(FieldNames.KEY_TITLE) != null)
				System.out.println("Title: " + document.get(FieldNames.KEY_TITLE));
			if (document.get(FieldNames.KEY_SUMMARY) != null)
				System.out.println("Summary: " + document.get(FieldNames.KEY_SUMMARY));
			System.out.println("Path: " + document.get(FieldNames.KEY_PATH));
			System.out.println("Last Modifified: " + document.get(FieldNames.KEY_LAST_MODIFIED));
			System.out.println("Relevance Score: " + result.score);
			System.out.println("");
			
			cnt++;
		}
	}
	
}
