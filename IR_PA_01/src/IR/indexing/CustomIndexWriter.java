package IR.indexing;

import Tools.RecusiveFiles;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.HashSet;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class CustomIndexWriter extends IndexWriter{
	
	public static final String FIELD_FILE_PATH = "path";
	public static final String FIELD_FILE_CONTENTS = "contents";
	
	private File corpusDirectory;

	public CustomIndexWriter(String indexDirectory, String corpusDirectory) throws IOException {
		super(
			FSDirectory.open(Paths.get(indexDirectory)), 
			new IndexWriterConfig(new StandardAnalyzer())
		);
		
		this.corpusDirectory = new File(corpusDirectory);	
	}
	
	public void createIndex() throws IOException {
		HashSet<File> files = RecusiveFiles.getFilesfromPath(this.corpusDirectory);
		for (File file : files) {
			Document document = new Document();

			String path = file.getCanonicalPath();
			System.out.println("Current File: " + file.getCanonicalPath());
			document.add(new StringField(FIELD_FILE_PATH, path, Field.Store.YES));

			Reader reader = new FileReader(file);
			document.add(new TextField(FIELD_FILE_CONTENTS, reader));

			this.addDocument(document);
		}
	}

}
