import Tools.RecusiveFiles;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

public class Main {
	public static final String PATH_TO_CORPUS = "/home/himanshoo/git/repository/IR_PA_01/Corpus";
	
	
	public static final String PATH_TO_INDEX = "/home/himanshoo/git/repository/IR_PA_01/Index";

	public static final String FIELD_FILE_PATH = "path";
	public static final String FIELD_FILE_CONTENTS = "contents";
	
	public static void createIndex(HashSet<File> files) throws CorruptIndexException, LockObtainFailedException, IOException {
		Analyzer analyzer = new StandardAnalyzer();
		Directory index = FSDirectory.open(Paths.get(PATH_TO_INDEX));
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = new IndexWriter(index, config);
		for (File file : files) {
			Document document = new Document();

			String path = file.getCanonicalPath();
			System.out.println("Current File :" + file.getCanonicalPath());
			document.add(new StringField(FIELD_FILE_PATH, path, Field.Store.YES));

			Reader reader = new FileReader(file);
			document.add(new TextField(FIELD_FILE_CONTENTS, reader));

			indexWriter.addDocument(document);
		}
		indexWriter.commit();
		//System.out.println("The field names" + indexWriter.getFieldNames());
		indexWriter.close();
	}
	
	public static void main(String[] args) throws CorruptIndexException, LockObtainFailedException, IOException {
		// TODO Auto-generated method stub
		File dir = new File(PATH_TO_CORPUS);
		HashSet<File> files = RecusiveFiles.getFilesfromPath(dir);
		//System.out.println(files);
		Main.createIndex(files);
		System.out.println("TERMINATED");
	}

}
