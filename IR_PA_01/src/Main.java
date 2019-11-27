import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.queryparser.classic.ParseException;

import IR.InformationRetriever;

public class Main {
	
	public static final String PATH_TO_CORPUS = "./Corpus";
	public static final String PATH_TO_INDEX = "./Index";
	
	public static void main(String[] args) throws IOException, ParseException {
		
		if (Files.notExists(Paths.get(PATH_TO_INDEX))) {
			Files.createDirectory(Paths.get(PATH_TO_INDEX));
		}
		
		FileUtils.cleanDirectory(new File(PATH_TO_INDEX));
		
		InformationRetriever informationRetriever = new InformationRetriever(PATH_TO_INDEX, PATH_TO_CORPUS);
		
		try(Scanner scanner = new Scanner(System.in)) {
			while(true) {
				System.out.println("Type in your query (-1 to close it): ");
				String query = scanner.nextLine();
				if (query.equals("-1")) {
					System.out.println("Thank you, bye!");
					break;
				}
				informationRetriever.retrieval(query);
			}
		}
		
	}

}
