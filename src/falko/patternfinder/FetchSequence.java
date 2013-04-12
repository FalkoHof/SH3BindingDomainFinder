package falko.patternfinder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class FetchSequence extends Thread {
	
	OneProtein prot;
	String id;
	int setCounter;
	boolean isReady = false;
		
	
	public FetchSequence(String s, int i){		
		super("Set: " + (i+1) + " ID: " + s);
		id = s;
		setCounter = i;
		
	}
	
	
	public void run(){
		
		try {
			
			URL ebiDas = new URL("http://www.ebi.ac.uk/das-srv/uniprot/das/uniprot/sequence?segment=" + id);
			BufferedReader in = new BufferedReader(new InputStreamReader(ebiDas.openStream()));
			String inputLine;
			String sequence="";
					
			while ((inputLine = in.readLine()) != null){
				if (inputLine.contains("<SEQUENCE"))
					sequence = (inputLine.split(">")[1]).split("<")[0];
			}
			
			prot = new OneProtein();
			prot.setId(id);
			prot.setSequence(sequence);
			isReady = true;
			System.out.println("Thread Name: " + super.getName());
			in.close();
			} catch (Exception e) {
				try {
					System.out.println("ERROR in Thread " + super.getName() + " Trying to fetch the seuence again...");
					this.sleep(500);
					run();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		
	}

	

	public OneProtein getProt() {
		return prot;
	}


	public boolean isReady() {
		return isReady;
	}
	
	
	

}
