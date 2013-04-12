package falko.patternfinder;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;


public class Statistics {
	
	double meanSH3bindingMotifRandom; double sdDevRandomstatsRandom; int sizeRandomSet = PatternFinder.randomProteinList.size(); double [] averagesRandom; int biggerThan = 0;
	
	double meanSH3bindingMotifTest; int sizeTestSet; String occurcences ="";
	
	double meanSH3bindingMotifProteome; int sizeWholeProteome;
	
	double TestSetSum; double TestSetN;
	
	
	DescriptiveStatistics statsTest = new DescriptiveStatistics();
	DescriptiveStatistics statsRandom = new DescriptiveStatistics();



	public void doStatistics(String s){
				
		if(s.equals("testSet"))	
			doStatisticsTestSet();
		
		if(s.equals("randomSet"))
			doStatisticsRandomSet();
		
		
		if(s.equals("wholeProteome"))
			doStatisticsWholeProteome();
		
		
		
	}
	
	public void printResults(){
		System.out.println("Mean random set: " +  meanSH3bindingMotifRandom+  "\t" + "Standard Deviation random set: " + sdDevRandomstatsRandom );
		System.out.println("Mean whole Proteome set: " + meanSH3bindingMotifProteome + "\t" + "Size whole Proteome: " + sizeWholeProteome );
		System.out.println("Mean Test set: " + meanSH3bindingMotifTest);
		System.out.println("Sum: " + TestSetSum + "N: " + TestSetN + "HitlistSize: " + sizeTestSet);

		System.out.println("Occourences of values higher than the Test Set : " + biggerThan + "/"  + sizeRandomSet );
	
	}
	
	public void saveResults(){		
		String results = ("Mean random set: " +  meanSH3bindingMotifRandom+  "\t" + "Standard Deviation random set: " + sdDevRandomstatsRandom + "\n" + 
				"Mean test set: " + meanSH3bindingMotifTest + "\n" + "Occourences of values higher than the Test Set : " + biggerThan + "/"  + sizeRandomSet);
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("resultsPatternFinder.txt"));
			out.write(results);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	
	
	private void doStatisticsWholeProteome(){
		
		
		ArrayList<Integer>booleanHitList = new ArrayList<Integer>();
		
		for (OneProtein prot : PatternFinder.wholeProteomeList){
			
			if((prot.getCounterClass1() !=0) || (prot.getCounterClass2() != 0))
				booleanHitList.add(1);
			else
				booleanHitList.add(0);			
		}
		DescriptiveStatistics stats = new DescriptiveStatistics();
				
		for (int v: booleanHitList)
			stats.addValue((double)v);
		
		meanSH3bindingMotifProteome = (stats.getMean())*100;
		sizeWholeProteome = booleanHitList.size();

		
		
		
	}

	
	
	
	private void doStatisticsTestSet() {
		
		ArrayList<Integer>booleanHitList = new ArrayList<Integer>();
		
		for (OneProtein prot : PatternFinder.testProteinList){
			
			if((prot.getCounterClass1() !=0) || (prot.getCounterClass2() != 0))
				booleanHitList.add(1);
			else{
				booleanHitList.add(0);
				System.out.println(prot.getId());
				}
		}
		DescriptiveStatistics stats = new DescriptiveStatistics();
				
		for (int v: booleanHitList)
			stats.addValue((double)v);
		
		meanSH3bindingMotifTest = (stats.getMean())*100;
		sizeTestSet = booleanHitList.size();
		TestSetSum = stats.getSum();
		TestSetN = stats.getN();
		
		
	}


	private void doStatisticsRandomSet() {
		
		double[] averages = new double[PatternFinder.randomProteinList.size()];
				
		for (int i = 0; i<PatternFinder.randomProteinList.size(); i++){
		
			int counterSize = 0; int counterHits = 0;
			
			for (OneProtein prot : PatternFinder.randomProteinList.get(i)){
				if((prot.getCounterClass1() !=0) || (prot.getCounterClass2() != 0))
					counterHits++;
				counterSize++;
			}
			
			System.out.println("hits: " + counterHits + " size: " + counterSize );
			double average = ((counterHits*1.0)/counterSize);
			statsRandom.addValue(average);
			averages[i] = average;
		}
		
		for (int i = 0; i<averages.length; i++){
			if (averages[i] >= meanSH3bindingMotifTest)
				biggerThan++;
		}
		
		meanSH3bindingMotifRandom = statsRandom.getMean()*100.0;
		sdDevRandomstatsRandom = statsRandom.getStandardDeviation()*100.0;
	}
	
}
