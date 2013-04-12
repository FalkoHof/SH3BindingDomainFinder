package falko.patternfinder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


public class DataGeneration {
	
	HashMap<String,ArrayList<String>> flyIdMap = new HashMap <String, ArrayList<String>>();
	ArrayList<String> flyIds = new ArrayList<String>();
	ArrayList<String> uniProtIds = new ArrayList<String>();
	ArrayList<ArrayList<String>>randomUniProtIdList = new ArrayList<ArrayList<String>>();
	final static int MAX_THREADS = ScriptProperties.getInstance().getNumberOfThreads();
	final static int NUMBER_RANDOMSETS = ScriptProperties.getInstance().getNumberOfRandomSets();


	public void parseFlyIdsUniprotList(){
	
	try {
		
		File file = new File("files/fbgn_NAseq_Uniprot_fb_2012_04.tsv");
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line = reader.readLine();
		String flyId = null;
		String uniProtId = null;
		ArrayList<String> uniprotList;
		
		while (line != null){
			
			if (!line.contains("#")){
				String[] temp = line.split("\t");
				flyId = temp[1];
			
				if (temp.length == 5){
					uniProtId = temp[4];
						
					if (flyIdMap.containsKey(flyId)){
						uniprotList = new ArrayList<String>();
						uniprotList = flyIdMap.get(flyId);				
						uniprotList.add(uniProtId);
						flyIdMap.put(flyId, uniprotList);
					}
					else{
						uniprotList = new ArrayList<String>();
						uniprotList.add(uniProtId);
						flyIdMap.put(flyId, uniprotList);
					}
				}
			
		}
		line = reader.readLine();		
	}
			
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}		
	}
	
	
	public void generateRandomUniprotIds(){
		ArrayList<String>FlyIds = new ArrayList<String>();
	
	
		FlyIds.addAll(flyIdMap.keySet());
		Random rn = new Random();	
		
		ArrayList<String>randomUniProtIds;
		
		for (int i = 0; i<NUMBER_RANDOMSETS; i++){
			
			randomUniProtIds = new ArrayList<String>();
			HashSet<Integer>randomIntSet = new HashSet<Integer>();

			while (randomIntSet.size() < 49){
				int randomInt = rn.nextInt(FlyIds.size());
				randomIntSet.add(randomInt);
			}
			//System.out.println("Size randomInSet: " + randomIntSet.size());
			for (int x : randomIntSet)
				randomUniProtIds.addAll(flyIdMap.get(FlyIds.get(x)));

			randomUniProtIdList.add(randomUniProtIds);
		}
		
		System.out.println("Size list of test sets :" + randomUniProtIdList.size());
	}
	
	
	public void parseWholeProteome(){
		
	try{	
		File file = new File("files/dmel-all-translation-r5.46.fasta");
		BufferedReader reader = new BufferedReader(new FileReader(file));			
		
		String sequence = "";
		String id;
		
		String line = (reader.readLine()).trim();
		id = ((line.split(" "))[0]).substring(1);
		OneProtein protein;
		while(line != null){
			
			if (line.contains(">")){
				
				if (sequence != ""){
					protein = new OneProtein();
					protein.setId(id);
					protein.setSequence(sequence);
					PatternFinder.wholeProteomeList.add(protein);
					id = ((line.split(" "))[0]).substring(1);
					sequence = "";
				}
			}					
			else
				sequence = sequence + line;
			
			line = reader.readLine();
			if (line != null)
				line.trim();
	}
			
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
		
		
		
		
		
	}
	
	public void convertFlyIdtoUniprot(){
		for (String id : flyIds){
			
			if (flyIdMap.containsKey(id)){
				uniProtIds.addAll(flyIdMap.get(id));
			}
			else{
				System.out.println("ID missing!");
				break;
			}
		}
		
		
		for (String s: uniProtIds){
			System.out.println(s);
		}
		System.out.println("Number of UniProtIds :" + uniProtIds.size());
		
	}
	
	
	
	public void getUniprotSequences(){
		
		try {
			
			for (int i = 0; i< randomUniProtIdList.size(); i++){
				
				ArrayList<String> randomIdSet = new ArrayList<String>(randomUniProtIdList.get(i));	
				ArrayList<FetchSequence> threadList = new ArrayList<FetchSequence>();
				List<OneProtein> randomProteinSetList = Collections.synchronizedList(new ArrayList<OneProtein>());
				
				System.out.println("Size Random ID Set: " + randomIdSet.size());
				for (int s = 0; s<randomIdSet.size(); s++){
				
					if (threadList.size() < MAX_THREADS){
						FetchSequence fs = new FetchSequence(randomIdSet.get(s), i);
						fs.start();
						threadList.add(fs);
					} 
					//else if(threadList.size() == MAX_THREADS || ((s+1) == randomIdSet.size())){
					else{
						System.out.println("active threads: " + Thread.activeCount());						
						FetchSequence fs = new FetchSequence(randomIdSet.get(s), i);
						fs.start();
						threadList.add(fs);
						for (int t = 0; t < threadList.size(); t++){
									FetchSequence thread = threadList.get(t);
									thread.join();									
									if (thread.getProt() != null){
											randomProteinSetList.add(thread.getProt());
									}	
							}
						threadList = new ArrayList<FetchSequence>();	
					}
				}
				
				for (int t = 0; t < threadList.size(); t++){
					FetchSequence thread = threadList.get(t);
					thread.join();					
					if (thread.getProt() != null){
							randomProteinSetList.add(thread.getProt());
					}	
				}
					
							
				ArrayList<OneProtein>randomProteinSetList2 = new ArrayList<OneProtein>();
				
				
				for (int in = 0; in< randomProteinSetList.size(); in++){
					randomProteinSetList2.add(randomProteinSetList.get(in));
				}
				PatternFinder.randomProteinList.add(randomProteinSetList2);
				
		}
			
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	
	public void parseTestSet(){
		
		try {
			File file = new File("files/cep89preys.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));			
			
			String sequence = "";
			String id;
			
			String line = (reader.readLine()).trim();
			id = ((line.split(" "))[0]).substring(1);
			System.out.println(id);
			
			OneProtein protein;
			while(line != null){
				
				if (line.contains(">")){
					
					if (sequence != ""){
						protein = new OneProtein();
						protein.setId(id);
						protein.setSequence(sequence);
						PatternFinder.testProteinList.add(protein);
						id = ((line.split(" "))[0]).substring(1);
						System.out.println(id);

						sequence = "";
					}
				}					
				else
					sequence = sequence + line;
				
				line = reader.readLine();
				if (line != null)
					line.trim();
		}
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	
	}

	
	
}
