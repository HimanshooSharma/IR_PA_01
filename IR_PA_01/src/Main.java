import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.queryparser.classic.ParseException;

import IR.InformationRetriever;
import dnl.utils.text.table.TextTable;

public class Main {
	
	public static final String INDEX_DIRECTORY = "./Index";
	public static final String[] RETRIEVAL_RESULTS_COLUMNS = {"Rank", "Path", "Last Modified", "Ranking Score", "Title", "Summary"};
	
	public static void main(String[] args) throws IOException, ParseException {
		
		String corpusDirectory = args[0];
		
		if (Files.notExists(Paths.get(INDEX_DIRECTORY))) {
			Files.createDirectory(Paths.get(INDEX_DIRECTORY));
		}
		
		FileUtils.cleanDirectory(new File(INDEX_DIRECTORY));
		
		InformationRetriever informationRetriever = new InformationRetriever(INDEX_DIRECTORY, corpusDirectory);
		
		try(Scanner scanner = new Scanner(System.in)) {
			while(true) {
				System.out.println("Type in your query (-1 to close it): ");
				String query = scanner.nextLine();
				if (query.equals("-1")) {
					System.out.println("Thank you, bye!");
					break;
				}
				String[][] results = informationRetriever.retrieval(query);
				TextTable table = new TextTable(RETRIEVAL_RESULTS_COLUMNS, results);
				table.printTable();
				System.out.println();
			}
		}
		
	}

}
