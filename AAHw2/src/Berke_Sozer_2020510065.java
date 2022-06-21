import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Berke_Sozer_2020510065 {

	public static void readAndFillArray(String fileName,ArrayList<Piece> pieces) throws IOException
	{
		//.csv file reading and object creation class
		
		File file = new File(fileName);
		Scanner scFile = new Scanner(file,"UTF-8"); 
		 
		while (scFile.hasNext())  
		{  
		  String input = scFile.nextLine();
		  String line[] = input.split(","); // splitting between ","
		  
		  if (line[0].equals("Hero")) 
		  {			  			  
			  //skipping first line(information line)
			  continue;
		  }
		  else 
		  {			  			  
			  Piece piece = new Piece(line[0],line[1],Integer.parseInt(line[2]),Integer.parseInt(line[3]));
			  pieces.add(piece);
		  }		  
		}
		scFile.close();
	}
	public static void greedyApproach(int gold, int max_level, int piece_count,ArrayList<Piece>pieces)
	{
		//greedy approach according to attack point/gold value
		//this algorithm aims to build the most price/performance army
		
		ArrayList<Piece> copyPieces = new ArrayList<Piece>(pieces);
		ArrayList<Piece> usablePieces = new ArrayList<Piece>();
		int c = 0;
		while (c/10!=max_level) //creating available pieces array according to max level and piece count inputs
		{
			for (int i = c; i < c+piece_count; i++) 
			{
				usablePieces.add(copyPieces.get(i));
			}
			c=c+10;		
		}
			int goldSpent = 0;
			int totalAP = 0;
			while (true) 
			{
				Piece bestPiece = null;
				int highestPP = 0; // price-performance
				int storeIndex = 0; // to not the lose best piece index
				for (int i = 0; i < usablePieces.size(); i++) 
				{				
					if (usablePieces.get(i) == null) {
						break;
					}
					//checking ap/gold
					if (usablePieces.get(i).getAp() / usablePieces.get(i).getGold() > highestPP && (gold > usablePieces.get(i).getGold())) 
					{												
						bestPiece = usablePieces.get(i);
						storeIndex = i;
					}
				}
				if (bestPiece==null) 
				{
					break;
				}
				//checking if that type of piece is in the army already or not
				String usedType = bestPiece.getType();
				for (int i = 0; i < usablePieces.size(); i++) 
				{
					if (usablePieces.get(i)!=null && usablePieces.get(i).getType().equals(usedType)) 
					{
						usablePieces.set(i,null);
					}
				}
				//updating gold,gold spent and total AP informations for displaying
				usablePieces.set(storeIndex,null);
				gold = gold - bestPiece.getGold();
				goldSpent += bestPiece.getGold();
				totalAP += bestPiece.getAp();
				System.out.println(bestPiece.getHero()+ " (" + bestPiece.getType()+ ", " + bestPiece.getGold() + ", " + bestPiece.getAp() + ") ");
	
		}
				System.out.println("Gold spent: " + goldSpent);
				System.out.println("Total Attack Point of the army: " + totalAP);
		
	}
	public static void randomApproach(int gold, int max_level, int piece_count,ArrayList<Piece>pieces)
	{		
			//random approach builds an army getting random pieces from the available piece pool
			ArrayList<Piece> copyPieces = new ArrayList<Piece>(pieces);		
			ArrayList<Piece> usablePieces = new ArrayList<Piece>();
			int c = 0;
			int goldSpent = 0;
			int totalAP = 0;
			while (c/10!=max_level) 
			{
				Random rng = new Random(); 
				int randomNumber = rng.nextInt(piece_count); // creating the random number every iteration
				for (int i = c; i < c+piece_count; i++) 
				{
					if (copyPieces.get(i)!=null && gold > copyPieces.get(i).getGold()) 
					{
						usablePieces.add(copyPieces.get(i+randomNumber));
						gold = gold - (copyPieces.get(i+randomNumber).getGold());
						goldSpent += copyPieces.get(i+randomNumber).getGold();
						totalAP += copyPieces.get(i+randomNumber).getAp();
						break;
					}
				}
				c=c+10;
			}
			//displaying the army
			for (int i = 0; i < usablePieces.size(); i++) 
			{
				System.out.println(usablePieces.get(i).getHero()+ " (" + usablePieces.get(i).getType()+ ", " + usablePieces.get(i).getGold() + ", " + usablePieces.get(i).getAp() + ") ");
			}
			System.out.println("Gold spent: " + goldSpent);
			System.out.println("Total Attack Point of the army: " + totalAP);
	
	}
	public static void dynamicProgrammingApproach(int gold, int max_level, int piece_count,ArrayList<Piece>pieces)
	{
		//dynamic programming approach aims to build an army with the highest total AP value
		//it may not be the best price/performance army but its always the strongest army compared to other algorithms
		ArrayList<Piece> dpPool = new ArrayList<Piece>();
		int c = 0;
		int goldSpent = 0;
		int totalAP = 0;
		while (c/10!=max_level) //creating the available piece pool according to max level and piece count inputs
		{
			for (int i = c; i < c+piece_count; i++) 
			{
				dpPool.add(pieces.get(i));
			}
			c=c+10;		
		}
			while (true) 
			{
				int highestAP = 0; // using this variable to get the strongest piece each level
				Piece bestPiece = null;	
				int storeIndex = 0;
				for (int i = 0; i < dpPool.size(); i++) 
				{				
					if (dpPool.get(i) == null) {
						break;
					}
					//checking if that piece is the highest ap piece each iteration (each level)
					if (dpPool.get(i).getAp() > highestAP && (gold > dpPool.get(i).getGold())) 
					{												
						bestPiece = dpPool.get(i);
						storeIndex = i;
						highestAP = bestPiece.getAp();
					}
				}
				if (bestPiece==null) 
				{
					break;
				}
				//checking if every type of piece is not used more than one
				String usedType = bestPiece.getType();
				for (int i = 0; i < dpPool.size(); i++) 
				{
					if (dpPool.get(i)!=null && dpPool.get(i).getType().equals(usedType)) 
					{
						dpPool.set(i,null);
					}
				}
				
				dpPool.set(storeIndex,null);
				gold = gold - bestPiece.getGold();
				goldSpent += bestPiece.getGold();
				totalAP += bestPiece.getAp();
				System.out.println(bestPiece.getHero()+ " (" + bestPiece.getType()+ ", " + bestPiece.getGold() + ", " + bestPiece.getAp() + ") ");
				
			}
			System.out.println("Gold spent: " + goldSpent);
			System.out.println("Total Attack Point of the army: " + totalAP);
		
	}
	
	public static void main(String[] args) throws IOException {
		
		int GOLD_AMOUNT;
		int MAX_LEVEL_ALLOWED;
		int NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL;
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		//using scanner to get inputs from the user(gold,level,and piece count)
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter starting gold amount:");
		while(true)	
		{
			GOLD_AMOUNT = sc.nextInt();
			if (GOLD_AMOUNT<=1200 && GOLD_AMOUNT>=5) 
			{
				break;
			}			
			System.out.println("Please enter a number between 5 and 1200!");		
		}
		System.out.println("Enter maximum allowed level:");
		while(true)	
		{
			MAX_LEVEL_ALLOWED = sc.nextInt();
			if (MAX_LEVEL_ALLOWED<=9 && MAX_LEVEL_ALLOWED>=1) 
			{
				break;
			}			
			System.out.println("Please enter a number between 1 and 9!");		
		}
		System.out.println("Enter number of available pieces per level:");
		while(true)	
		{
			NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL = sc.nextInt();
			if (NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL<=25 && NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL>=1) 
			{
				break;
			}			
			System.out.println("Please enter a number between 1 and 25!");		
		}
		readAndFillArray("input_1.csv",pieces);
		
		//DISPLAY SECTION 
		//NOTE: Nanosecond result is divided by 1000000d to show more digits in milisecond form.
		
		System.out.println(" ");
		System.out.println("Trial #1");
		System.out.println(" ");
		System.out.println("User (Dynamic Programming)");
		System.out.println(" ");
		long DP1StartTime = System.nanoTime();
		dynamicProgrammingApproach(GOLD_AMOUNT,MAX_LEVEL_ALLOWED,NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL,pieces);
		long DP1EndTime = System.nanoTime();
		System.out.println("Execution time: " + (DP1EndTime-DP1StartTime) / 1000000d + " ms");
		System.out.println(" ");
		System.out.println("Computer (Greedy Approach)");
		System.out.println(" ");
		long GA1StartTime = System.nanoTime();
		greedyApproach(GOLD_AMOUNT,MAX_LEVEL_ALLOWED,NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL,pieces);
		long GA1EndTime = System.nanoTime();
		System.out.println("Execution time: " + (GA1EndTime-GA1StartTime) / 1000000d + " ms");
		System.out.println(" ");	
		
		
		System.out.println("Trial #2");
		System.out.println(" ");
		System.out.println("User (Dynamic Programming)");
		System.out.println(" ");
		long DP2StartTime = System.nanoTime();
		dynamicProgrammingApproach(GOLD_AMOUNT,MAX_LEVEL_ALLOWED,NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL,pieces);
		long DP2EndTime = System.nanoTime();
		System.out.println("Execution time: " + (DP2EndTime-DP2StartTime) / 1000000d + " ms");
		System.out.println(" ");
		System.out.println("Computer (Random Approach)");
		System.out.println(" ");
		long RA1StartTime = System.nanoTime();
		randomApproach(GOLD_AMOUNT,MAX_LEVEL_ALLOWED,NUMBER_OF_AVAILABLE_PIECES_PER_LEVEL,pieces);
		long RA1EndTime = System.nanoTime();
		System.out.println("Execution time: " + (RA1EndTime-RA1StartTime) / 1000000d + " ms");

	}

}

