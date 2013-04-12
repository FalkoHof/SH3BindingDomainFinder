package falko.patternfinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PatternFinder {
	
	static HashMap<String, Integer> motifMap  = new HashMap<String, Integer>();
	static HashMap<String, Integer> class1Map  = new HashMap<String, Integer>();
	static HashMap<String, Integer> class2Map = new HashMap<String, Integer>();

	public static List<ArrayList<OneProtein>> randomProteinList = Collections.synchronizedList(new ArrayList<ArrayList<OneProtein>>());
	public static ArrayList<OneProtein> testProteinList = new ArrayList<OneProtein>();
	//public static HashMap<String, OneProtein> wholeProteomeMap = new HashMap<String, OneProtein>();
	public static ArrayList<OneProtein>wholeProteomeList = new ArrayList<OneProtein>();
	
	static int seqCounter = 0;
	static double numberOfFlyIDs = 49.0;
	
	
	public static void main(String[] args) {	
		
		//parseFasta();
		
		DataGeneration data = new DataGeneration();
		System.out.println("Generating Data...");
			data.parseFlyIdsUniprotList();
			data.parseWholeProteome();
			data.generateRandomUniprotIds();
			data.getUniprotSequences();
			data.parseTestSet();

		PatternSearcher search= new PatternSearcher();
		System.out.println("Searching for SH3 binding motives...");
			search.findPattern("testSet");			
			search.findPattern("randomSet");
			search.findPattern("wholeProteome");
		
		Statistics stats = new Statistics();
			stats.doStatistics("testSet");
			stats.doStatistics("randomSet");
			stats.doStatistics("wholeProteome");
			stats.saveResults();
			stats.printResults();
	}
	
}
	
	
	



