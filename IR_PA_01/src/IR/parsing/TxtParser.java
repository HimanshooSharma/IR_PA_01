package IR.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import Tools.DateFormatter;

public class TxtParser implements Parser{
	
	private ArrayList<Field> fields;

	@Override
	public ArrayList<Field> parse(File file) throws IOException {
		this.fields = new ArrayList<Field>();
		
		String path = file.getCanonicalPath();
		this.fields.add(new StringField(FieldNames.KEY_PATH, path, Field.Store.YES));
		
		@SuppressWarnings("deprecation")
		String text = FileUtils.readFileToString(file);
		this.fields.add(new TextField(FieldNames.KEY_CONTENT, text, Field.Store.YES));
		
		String lastModified = DateFormatter.longToString(file.lastModified());
		this.fields.add(new TextField(FieldNames.KEY_LAST_MODIFIED, lastModified, Field.Store.YES));
		
		return this.fields;
	}

}
