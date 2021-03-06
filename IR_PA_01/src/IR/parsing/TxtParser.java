package IR.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

public class TxtParser extends StandardParser {
	
	@Override
	public ArrayList<Field> parse(File file) throws IOException {
		ArrayList<Field> fields = new ArrayList<Field>();
		fields.addAll(super.parse(file));
		
		@SuppressWarnings("deprecation")
		String text = FileUtils.readFileToString(file);
		fields.add(new TextField(FieldNames.KEY_CONTENT, text, Field.Store.YES));
		
		return fields;
	}

}
