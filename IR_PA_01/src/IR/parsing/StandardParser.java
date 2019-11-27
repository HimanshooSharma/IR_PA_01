package IR.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import Tools.DateFormatter;

public class StandardParser implements Parser {	

	@Override
	public ArrayList<Field> parse(File file) throws IOException {
		ArrayList<Field> fields = new ArrayList<Field>();

		String filename = file.getName();
		fields.add(new StringField(FieldNames.KEY_FILE_NAME, filename, Field.Store.YES));
		
		String path = file.getCanonicalPath();
		fields.add(new StringField(FieldNames.KEY_PATH, path, Field.Store.YES));
		
		String lastModified = DateFormatter.longToString(file.lastModified());
		fields.add(new TextField(FieldNames.KEY_LAST_MODIFIED, lastModified, Field.Store.YES));
		
		return fields;
	}

}
