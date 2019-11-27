package IR.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;

import Tools.DateFormatter;

public class StandardParser implements Parser {	

	@Override
	public ArrayList<Field> parse(File file) throws IOException {
		ArrayList<Field> fields = new ArrayList<Field>();

		String filename = file.getName();
		fields.add(new StoredField(FieldNames.KEY_FILE_NAME, filename));
		
		String path = file.getCanonicalPath();
		fields.add(new StoredField(FieldNames.KEY_PATH, path));
		
		String lastModified = DateFormatter.longToString(file.lastModified());
		fields.add(new StoredField(FieldNames.KEY_LAST_MODIFIED, lastModified));
		
		return fields;
	}

}
