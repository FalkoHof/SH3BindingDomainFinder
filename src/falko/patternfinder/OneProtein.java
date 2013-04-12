package falko.patternfinder;
public class OneProtein {
	
	
	String name;
	String id;
	String sequence;
	int counterprolinRich = 0;
	int counterClass1 = 0;
	int counterClass2 = 0;
	int sequenceLength = 0;
	
	
	public OneProtein(String name, String id,String sequence, int counterprolinRich, int counterClass1,	int counterClass2, int sequenceLength){
		this.name = name;
		this.id = id;
		this.sequence = sequence;
		this.counterprolinRich = counterprolinRich;
		this.counterClass1 = counterClass1;
		this.counterClass2 = counterClass2;
		this.sequenceLength = sequenceLength;
	}
	
	public OneProtein(){
		
	}

	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCounterprolinRich() {
		return counterprolinRich;
	}

	public void setCounterprolinRich(int counterprolinRich) {
		this.counterprolinRich = counterprolinRich;
	}

	public int getCounterClass1() {
		return counterClass1;
	}

	public void setCounterClass1(int counterClass1) {
		this.counterClass1 = counterClass1;
	}

	public int getCounterClass2() {
		return counterClass2;
	}

	public void setCounterClass2(int counterClass2) {
		this.counterClass2 = counterClass2;
	}

	public int getSequenceLength() {
		return sequenceLength;
	}

	public void setSequenceLength(int sequenceLength) {
		this.sequenceLength = sequenceLength;
	}
}
