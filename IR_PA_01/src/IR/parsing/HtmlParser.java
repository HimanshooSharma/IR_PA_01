package IR.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Tools.DateFormatter;


public class HtmlParser implements Parser {
	
	private ArrayList<Field> fields;
	private Document htmlFile;

	@Override
	public ArrayList<Field> parse(File file) throws IOException {
		this.fields = new ArrayList<Field>();
		this.htmlFile = Jsoup.parse(file, "utf-8");
		
		try {
			
			String path = file.getCanonicalPath();
			this.fields.add(new StringField(FieldNames.KEY_PATH, path, Field.Store.YES));
			
			String title = this.htmlFile.title();
			this.fields.add(new TextField(FieldNames.KEY_TITLE, title, Field.Store.YES));
			
			String body = htmlFile.body().text();
			this.fields.add(new TextField(FieldNames.KEY_CONTENT, body, Field.Store.YES));
			
			String summary = htmlFile.getElementsByTag("summary").text();
			this.fields.add(new TextField(FieldNames.KEY_SUMMARY, summary, Field.Store.YES));
			
			String lastModified = DateFormatter.longToString(file.lastModified());
			this.fields.add(new TextField(FieldNames.KEY_LAST_MODIFIED, lastModified, Field.Store.YES));
			
			StringBuilder dates = new StringBuilder();
			dates.setLength(0);
			Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
			Elements elements = this.htmlFile.getElementsMatchingText(pattern);
			List<Element> finalElements = elements.stream().filter(elem -> isLastElem(elem, pattern)).collect(Collectors.toList());
            finalElements.stream().forEach(elem ->
                dates.append(elem.html())
            );
            if (dates.length() > 0) 
            	this.fields.add(new TextField(FieldNames.KEY_DATE, dates.toString(), Field.Store.YES));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
			
		
		return this.fields;
	}
	
	private boolean isLastElem(Element elem, Pattern pattern) {
        return elem.getElementsMatchingText(pattern).size() <= 1;
    }

}
