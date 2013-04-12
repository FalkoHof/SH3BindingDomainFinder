package falko.patternfinder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatternSearcher {
	
	
	public void findPattern(String s){
		
		if(s.equals("randomSet"))
			for (ArrayList<OneProtein> prot : PatternFinder.randomProteinList)
				searchForPattern(prot);	
			
		if(s.equals("testSet"))
			searchForPattern(PatternFinder.testProteinList);
		
		
		if(s.equals("wholeProteome"))
			searchForPattern(PatternFinder.wholeProteomeList);
	}




	private void searchForPattern(ArrayList<OneProtein> proteinList){
		
		Pattern prolinRichMotiv = Pattern.compile("P[A-Z]{2}P");
		Pattern sh3Class1Motiv = Pattern.compile("[RK][A-Z]{2}P[A-Z]{2}");
		Pattern sh3Class2Motiv = Pattern.compile("P[A-Z]{2}P[RK]");
		
		/*
		
		[RK][A-Z]{2}P[A-Z]{2} 	+xxPxxP (class I motif)
		P[A-Z]{2}P[RK]			PxxPx+	(class II motif)
		‘x’ denotes any of 20 amino acids, ‘+’ positively charged amino acids (R/K)
		
		*/
					
		for (OneProtein protein : proteinList){
		
			int counterprolinRich = 0;
			int counterClass1 = 0;
			int counterClass2 = 0;
			
			String sequence = protein.getSequence();
				
			Matcher prolinRichMotif = prolinRichMotiv.matcher(sequence);
			Matcher class1Motif = sh3Class1Motiv.matcher(sequence);
			Matcher class2Motif = sh3Class2Motiv.matcher(sequence);
			
			
			while (prolinRichMotif.find())
				counterprolinRich++;	 
			protein.setCounterprolinRich(counterprolinRich);
			
			
			while (class1Motif.find())
				counterClass1++;
			protein.setCounterClass1(counterClass1);
			
		 
			while (class2Motif.find())
				counterClass2++;
			protein.setCounterClass2(counterClass2);
			

			
		}	
	}
}
