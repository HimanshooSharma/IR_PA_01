package IR.indexing;

import Tools.RecusiveFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import IR.parsing.HtmlParser;
import IR.parsing.Parser;
import IR.parsing.TxtParser;

public class CustomIndexWriter extends IndexWriter{
	
	public CustomIndexWriter(Directory index, IndexWriterConfig config) throws IOException {
		super(index, config);
	}

	public void createIndexFromCorpusDirectory(File corpusDirectory) throws IOException {
		HashSet<File> files = RecusiveFiles.getFilesfromPath(corpusDirectory);
		Parser txtParser = new TxtParser();
		Parser htmlParser = new HtmlParser();
		for (File file : files) {
			System.out.println(file.getCanonicalPath());
			
			Document document = new Document();

			ArrayList<Field> fields = null;
			if (file.getName().endsWith(".txt")) {
				fields = txtParser.parse(file);
			} 
			else if (file.getName().endsWith(".html") || file.getName().endsWith(".htm")) {
				fields = htmlParser.parse(file);
			}
			else 
				continue;
			
			for (Field field : fields) 
				document.add(field);

			this.addDocument(document);
		}
		
	}

}
