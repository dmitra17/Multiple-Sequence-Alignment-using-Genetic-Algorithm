import java.util.Arrays;

public class GeneticAlgorithm {
	String[][] seq = new String[125][3];
	String[] initialSeq = {"QWGDNGFFKJPKXTTTTTTTTTT", "AAQWGDNGFFKJPKXYYYYYYYY", "BBBQWGDNGFFKJPKXZZZZZZZ"};
	String[][] child = new String[125][3];
	String[][] child1 = new String[125][3];
	double random;
	int randomVal;
	String newSeq = null;
	int extraSpace = 5;
	int totalLength = initialSeq[0].length()+extraSpace;
	String[] abc = {"QWGDNGFFKJPKXTTTTTTTTTT", "AAQWGDNGFFKJPKXYYYYYYYY", "BBBQWGDNGFFKJPKXZZZZZZZ"};
	int[] scoreMatrix = new int[125];
	int[] childScoreMatrix = new int[125];
	int score = 0;
	int totalScore = 0;
	int max_score = 0;
	int max_index;
	int[] modifiedScoreMatrix = new int[125];
	double[] rouletteRange = new double[125];
	double[] randomCrossOver = new double[250];
	Double[] sortedRandomCrossOver = new Double[250];
	int[] parentMappingCrossOver = new int[250];
	Integer[] indexes;
	Integer[] indexesSelection;
	String[] sortedSelectionfinal = new String[250];
	Integer[] dummyindexes;
	int[] probIndex = new int[50];
	Integer[] sortedscores = new Integer[250];
	String[] sortedAlignement = new String[250];
	String[][] tempSeq = new String[125][3];
	
	
	public void initialSequenceWithGaps(){
		for(int i=0; i<3; i++){
			for(int j=0; j<extraSpace; j++){
				
				initialSeq[i] = initialSeq[i].concat("_");
			}
		}
	}
	
	public void initialSequenceDuplicateWithGaps(){
		for(int i=0; i<3; i++){
			for(int j=0; j<extraSpace; j++){
				
				abc[i] = abc[i].concat("_");
			}
		}
	}
	
	public int randomValue(int length){
		    random = Math.random()*length;
		    randomVal = (int) Math.floor(random);
		return randomVal;
	}
	
	public int randomValueForCrossOver(){
	    random = Math.random()*124;
	    randomVal = (int) Math.floor(random);
	return randomVal;
	}
    
    public void population(){
    	for(int i=0; i<125;i++){
    		for(int p=0; p<3; p++){
    			initialSeq[p] = abc[p];
    		}
    		for(int j=0; j<3; j++){
    			for(int k=0; k<5; k++){
    				int value = randomValue(initialSeq[j].length());
    				
    				newSeq = initialSeq[j].substring(0,value);
    				newSeq = newSeq.concat("_");
    				newSeq = newSeq.concat(initialSeq[j].substring(value,totalLength-1));
    				
    				initialSeq[j]=newSeq;
    			}
    			//System.out.println(initialSeq[j]);
    			seq[i][j]=newSeq;
    		}
    	}
    	System.out.println("initial1 "+seq[0][0]);
    	System.out.println("initial2 "+seq[0][1]);
    	System.out.println("initial3 "+seq[0][2]);
    }
    
    public void crossOver(){
    	for(int i=0; i<125; i++){
	    	for(int j=0; j<3; j++){	    		
	    		
	    		child[i][j] = seq[parentMappingCrossOver[dummyindexes[i*2]]][j];
	    		String children = child[i][j].substring(0, child[i][j].length()/2);
	    		String childrenNextHalf = child[i][j].substring(child[i][j].length()/2, child[i][j].length());
	    		child1[i][j] = seq[parentMappingCrossOver[dummyindexes[i*2+1]]][j];
	    		/*child1[i][j] = seq[2][j];
	    		System.out.println(child1[i][j]);*/
	    		String children1 = "";
	    		String childrenNextHalf1 = "";
	    		int subStringLen = children.length();
	    		int count =0;
	    		for(int m=0; m<subStringLen; m++){
	    			if(children.charAt(m) != '_')
	    				count++;
	    		}
	    		for(int p=0; p<child1[i][j].length() && count>0; p++){
	    			if(child1[i][j].charAt(p) != '_'){
	    				count--;
	    			}
	    			children1 = children1 + child1[i][j].charAt(p);
	    		}
	    		childrenNextHalf1 = child1[i][j].substring(children1.length());
	    		child[i][j] = children.concat(childrenNextHalf1);
	    		child1[i][j] = children1.concat(childrenNextHalf);
	    	}
	    	int maxSeqLength = 0;
	    	int maxSeqLength1 = 0;
	    	for(int j=0; j<3; j++){
	    		if(maxSeqLength < child[i][j].length()){
	    			maxSeqLength = child[i][j].length();
	    		}
	    		if(maxSeqLength1 < child1[i][j].length()){
	    			maxSeqLength1 = child1[i][j].length();
	    		}
	    	}
	    	for(int j=0; j<3; j++){
	    		while(maxSeqLength > child[i][j].length()){
	    			child[i][j] = child[i][j].concat("_");
	    		}
	    		while(maxSeqLength1 > child1[i][j].length()){
	    			child1[i][j] = child1[i][j].concat("_");
	    		}
	    	}
    	}
    	//System.out.println(child1[0][0]);
    	//System.out.println(child1[0][1]);
    	//System.out.println(child1[0][2]);
    }
    
    public void scoringFunctionForChild() {
    	for(int i=0; i<125; i++){
    		int alignmentLength = child[i][0].length();
    		int alignmentLength1 = child1[i][0].length();
    		int childScore = 0;
    		int childScore1 = 0;
    		for(int j=0; j<alignmentLength; j++){
    			/*if(child[i][0].charAt(j) == child[i][1].charAt(j) && child[i][1].charAt(j) == child[i][2].charAt(j) && child[i][0].charAt(j) != '_'){
    				childScore++;
    			}*/
    			if((child[i][0].charAt(j) == child[i][1].charAt(j)) && child[i][0].charAt(j) != '_'){
    				childScore++;
    			}
    			if((child[i][1].charAt(j) == child[i][2].charAt(j)) && child[i][1].charAt(j) != '_'){
    				childScore++;
    			}
    			if((child[i][0].charAt(j) == child[i][2].charAt(j)) && child[i][0].charAt(j) != '_'){
    				childScore++;
    			}
    		}
    		for(int j=0; j<alignmentLength1; j++){
    			if(child1[i][0].charAt(j) == child1[i][1].charAt(j) && child1[i][1].charAt(j) == child1[i][2].charAt(j) && child1[i][0].charAt(j) != '_'){
    				childScore1++;
    			}
    		}
    		childScoreMatrix[i] = childScore;
    		if(childScore<childScore1)
    		{
    			child[i][0] = child1[i][0];
	    		child[i][1] = child1[i][1];
	    		child[i][2] = child1[i][2];
	    		childScoreMatrix[i] = childScore1;
    		}
    		
    		/*if(childScoreMatrix[i]>max_score)
    		{
    			max_score = childScoreMatrix[i];
    			max_index = i;
    		}*/
    	}
    	//System.out.println("child1 "+child[0][0]);
		//System.out.println("child2 "+child[0][1]);
		//System.out.println("child3 "+child[0][2]);
    }
    
    public void scoringFunction(){
    	for(int i=0; i<125; i++){
    		int alignmentLength = seq[i][0].length();
    		score = 0;
    		for(int j=0; j<alignmentLength; j++){
    			/*if(seq[i][0].charAt(j) == seq[i][1].charAt(j) && seq[i][1].charAt(j) == seq[i][2].charAt(j) && seq[i][0].charAt(j)!= '_'){
    				score++;
    				totalScore++;
    			}*/
    			if((seq[i][0].charAt(j) == seq[i][1].charAt(j)) && (seq[i][0].charAt(j)!= '_')){
    				score++;
    				totalScore++;
    			}
    			if((seq[i][1].charAt(j) == seq[i][2].charAt(j)) && (seq[i][1].charAt(j)!= '_')){
    				score++;
    				totalScore++;
    			}
    			if((seq[i][0].charAt(j) == seq[i][2].charAt(j)) && (seq[i][0].charAt(j)!= '_')){
    				score++;
    				totalScore++;
    			}
    		}
    		modifiedScoreMatrix[i] = totalScore;
    		scoreMatrix[i] = score;
    	//	System.out.print(modifiedScoreMatrix[i] +" ");
    	}
    	//System.out.println();
    	probabilityFunction();
    }
    
    public void combinedPopulation() {	
    	
    	
    	for(int i=0; i<125; i++){
    		sortedscores[i] = scoreMatrix[i];
    		sortedscores[i+125] = childScoreMatrix[i];
    	}
    	for(int i=0; i<125; i++){
    		sortedAlignement[i] = seq[i][0] + " " +seq[i][1] + " " +seq[i][2];
    		sortedAlignement[i+125] = child[i][0] + " " +child[i][1] + " " +child[i][2];
    	}
    	
    	ArrayIndexComparator2 comparator = new ArrayIndexComparator2(sortedscores);
    	indexesSelection = comparator.createIndexArray();
    	Arrays.sort(indexesSelection, comparator);
    	Arrays.sort(sortedscores);
    	
    	//for(int i=0; i<250; i++)
    	System.out.println("Scores : " +"   "+sortedscores[249]);
    	
    	for(int i=0; i<250; i++){
    		sortedSelectionfinal[i] = sortedAlignement[indexesSelection[i]];
    	}
    	
    	
    	/*for(int i=0; i<125; i++){
    	//	tm.put(scoreMatrix[i], seq[i][0] + " " +seq[i][1] + " " +seq[i][2]);
    		tm.put(childScoreMatrix[i], child[i][0] + " " +child[i][1] + " " +child[i][2]);
    	//	tm.put(i,"abc");
    	}
    	System.out.println(tm.size());
    	// Get a set of the entries
        Set set = tm.entrySet();
        
        // Get an iterator
        Iterator iterator = set.iterator();
        
        while(iterator.hasNext()) {
           Map.Entry me = (Map.Entry)iterator.next();
           System.out.print(me.getKey() + ": ");
           System.out.println(me.getValue());
           int j=0;
           sortedscores[j] = Integer.valueOf((Integer) me.getKey());
           sortedAlignement[j] = String.valueOf((String) me.getValue());
           j++;
        }*/
    	
    	
        double top = 125*2*0.2;
    	int topPopulation = (int)top;
    	
    	for(int i=0; i<topPopulation; i++){
    		// Storing in a temp array for time being...need to be moved to seq[i][j] ==> tempSeq
    		String[] temparr = sortedSelectionfinal[i].split("\\s+");
    		for(int j=0; j<3; j++){
    			tempSeq[i][j] = temparr[j];
    			//System.out.println(tempSeq[i][j]);
    		}
    	}
    	probabilityFunctionForSelection(topPopulation);
    	//System.out.println("final1 "+tempSeq[0][0]);
    	//System.out.println("final2 "+tempSeq[0][1]);
    	//System.out.println("final3 "+tempSeq[0][2]);
    	
    }
    
    public void probabilityFunctionForSelection(int top) {
    	int totalScoreSelection = 0;
    	for(int i=top; i<125*2; i++){
    		totalScoreSelection = totalScoreSelection + sortedscores[i];
    	}
    	
    	double[] rouletteSelection = new double[125*2-top];
    	
    	for(int i=0; i<125*2-top; i++){
    		rouletteSelection[i] = sortedscores[i+top]/totalScoreSelection;
    	}
    	
    	for(int i=0; i<125*2-top; i++){
    		double temp = 0;
    		temp = rouletteSelection[i] + temp;
    		rouletteSelection[i] = temp;
    	}
    	
    	double[] randomValSelection = new double[125*2-top];
    	for(int i=0; i<125*2-top; i++){
    		randomValSelection[i] = Math.random();
    	}
    	Arrays.sort(randomValSelection);
    	    	
    	int j=0;
    	int i=top;
    	while(i<125*2 && j<125-top){
    		if(randomValSelection[j] > sortedscores[i]){
    			i++;
    		}else{
    			String[] temparr1 = sortedSelectionfinal[i].split("\\s+");
    			for(int p=0; p<3; p++){
    			tempSeq[top+j][p] = temparr1[p];
    			}
    			j++;
    		}
    	}
    	for(int m=0; m<125; m++){
    		for(int k=0; k<3; k++){
    			seq[m][k] = tempSeq[m][k];
    			//System.out.println("Vivek" +m +seq[m][k]);
    		}
    	}
    }
    
    public void probabilityFunction(){
    	for(int i=0; i<125; i++){
    		rouletteRange[i] = (double)modifiedScoreMatrix[i]/totalScore;
    		randomCrossOver[i] = Math.random();
    		randomCrossOver[i+125] = Math.random();
    		sortedRandomCrossOver[i] = randomCrossOver[i];
    		sortedRandomCrossOver[i+125] = randomCrossOver[i+125];
    	//	System.out.print(rouletteRange[i] +" ");
    	//	System.out.print(randomCrossOver[i] + " ");
    	}
    	
    	//System.out.println();
    	ArrayIndexComparator comparator = new ArrayIndexComparator(sortedRandomCrossOver);
    	indexes = comparator.createIndexArray();
    	Arrays.sort(indexes, comparator);
    	
    	
    	/*for(int i=0; i<250; i++){
    		System.out.print(sortedRandomCrossOver[i] +" ");
    	}*/
    	/*System.out.println();
    	for(int i=0; i<250; i++){
    		System.out.print(indexes[i] +" ");
    	}*/
    	//System.out.println();
    	Arrays.sort(sortedRandomCrossOver);
    	for(int i=0; i<250; i++){
    	//	System.out.print(sortedRandomCrossOver[i] +" ");
    	}
    	
    	ArrayIndexComparator1 comparator1 = new ArrayIndexComparator1(indexes);
    	dummyindexes = comparator1.createIndexArray();
    	Arrays.sort(dummyindexes, comparator1);
    	
    	//System.out.println();
    	for(int i=0; i<250; i++){
    	//	System.out.print(dummyindexes[i] +" ");
    	}
    	
    	int j=0;
    	for(int i=0; i<2*125; i++){
    		while(sortedRandomCrossOver[i] >= rouletteRange[j]){
    			j++;
    		}
    		parentMappingCrossOver[i] = j;
    	}
    }
    
    public void mutation() {
    	
    	int count = 0;
    	for(int i=0; i<125*3; i++){
    		if(Math.random()<0.05) {
    			probIndex[count] = i;
    			count++;
    		}
    	}
    	for(int p=0; p<count; p++){
    		int i = probIndex[p]/3;
    		int j = probIndex[p]%3;
    		int mutationIndex = randomValue(seq[i][j].length());
    		
    		if(seq[i][j].charAt(mutationIndex)!='_'){
				seq[i][j] = seq[i][j].substring(0, mutationIndex+1) + "_" + seq[i][j].substring(mutationIndex+1);
				for(int m=0; m<j; m++) {
					seq[i][m] = seq[i][m] + "_";
				}
				for(int m=j+1; m<3; m++) {
					seq[i][m] = seq[i][m] + "_";
				}
			}else{
				seq[i][j] = seq[i][j].substring(0, mutationIndex) + seq[i][j].substring(mutationIndex+1);
				seq[i][j] = seq[i][j] + "_";
			}
    	}
    	//System.out.println("mutation1 "+seq[0][0]);
    	//System.out.println("mutation2 "+seq[0][1]);
    	//System.out.println("mutation3 "+seq[0][2]);
    }
    
    public void selection() {
    	combinedPopulation();
    }
    
    public static void main(String[] args) {
    	GeneticAlgorithm ga = new GeneticAlgorithm();
    	ga.initialSequenceDuplicateWithGaps();
    	ga.initialSequenceWithGaps();
    	ga.population();
    	for(int i=0; i<100; i++){
	    	ga.scoringFunction();
	    	ga.crossOver();
	    	ga.scoringFunctionForChild();
	    	ga.mutation();
	    	ga.selection();
    	}
    	int finalmaximum = 0;
    	int finalIndex = 0;
    	
    	for(int i=0; i<125; i++){
    		int alignmentLength = ga.seq[i][0].length();
    		int score = 0;
    		for(int j=0; j<alignmentLength; j++){
    			/*if(seq[i][0].charAt(j) == seq[i][1].charAt(j) && seq[i][1].charAt(j) == seq[i][2].charAt(j) && seq[i][0].charAt(j)!= '_'){
    				score++;
    				totalScore++;
    			}*/
    			if((ga.seq[i][0].charAt(j) == ga.seq[i][1].charAt(j)) && (ga.seq[i][0].charAt(j)!= '_')){
    				score++;
    			}
    			if((ga.seq[i][1].charAt(j) == ga.seq[i][2].charAt(j)) && (ga.seq[i][1].charAt(j)!= '_')){
    				score++;
    			}
    			if((ga.seq[i][0].charAt(j) == ga.seq[i][2].charAt(j)) && (ga.seq[i][0].charAt(j)!= '_')){
    				score++;
    			}
    		}
    		if(score>finalmaximum){
    			finalmaximum=score;
    			finalIndex = i;
    		}
    	}
    	
    	int tempLen = ga.seq[finalIndex][0].length();
    	
    	for(int k=0; k<tempLen; k++){
    		if(ga.seq[finalIndex][0].charAt(k) == ga.seq[finalIndex][1].charAt(k) &&
    				ga.seq[finalIndex][0].charAt(k) == ga.seq[finalIndex][2].charAt(k) &&
    				ga.seq[finalIndex][0].charAt(k) == '_'){
    			ga.seq[finalIndex][0] = ga.seq[finalIndex][0].substring(0,k) + ga.seq[finalIndex][0].substring(k+1);
    			ga.seq[finalIndex][1] = ga.seq[finalIndex][1].substring(0,k) + ga.seq[finalIndex][1].substring(k+1);
    			ga.seq[finalIndex][2] = ga.seq[finalIndex][2].substring(0,k) + ga.seq[finalIndex][2].substring(k+1);
    			tempLen--;
    			k--;
    		}
    	}
    	
    	System.out.println(ga.seq[finalIndex][0]);
    	System.out.println(ga.seq[finalIndex][1]);
    	System.out.println(ga.seq[finalIndex][2]);
    	
	}
}