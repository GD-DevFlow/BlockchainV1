package blockchain;


import java.util.ArrayList;
import com.google.gson.GsonBuilder;
import org.bouncycastle.*;

public class BlockchainMain {
	
	public static ArrayList<Block> blockChain = new ArrayList<Block>();
	public static int difficulty = 5;
	
	
	

	public static void main(String[] args) {
//https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa
		
		blockChain.add(new Block("This is my genesis block", "0"));
		System.out.println("Trying to Mine Block 1......");
		blockChain.get(0).mineBlock(difficulty);
		
		blockChain.add(new Block("This is my 2nd block", blockChain.get(blockChain.size()-1).getHash()));
		System.out.println("Trying to Mine Block 2...");
		blockChain.get(1).mineBlock(difficulty);
		
		blockChain.add(new Block("This is my 3rd block", blockChain.get(blockChain.size()-1).getHash()));
		System.out.println("Trying to Mine Block 3...");
		blockChain.get(2).mineBlock(difficulty);
		
		
		String BlockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
		System.out.println(BlockChainJson);
		
		
		Boolean checker = isChainValid();
		System.out.println("\nThis chain is valid: " + checker);
	}
	
	
	/**
	 * 
	 * @return Checks validity of the blockchain
	 */
	public static Boolean isChainValid()
	{
		
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		for(int i = 1; i < blockChain.size(); i++)
		{
			currentBlock = blockChain.get(i);
			previousBlock = blockChain.get(i-1);
			
			// Verifying the actual hash of the current block is the same as the one calculated after applying reapplying Sha
			if(!currentBlock.getHash().equals(currentBlock.calculateHash()))
			{
				System.out.println("Hashs of the current block do not match");
				return false;
			}
			
			// Verifying that the previousHash property in the current block is the same hash value property calculated by the previousBlock
			if(!currentBlock.getPreviousHash().equals(previousBlock.getHash()))
			{
				System.out.println("Hashs of the previous do not match");
				return false;
			}
			
			//Verifying if hash is solved
			if(!currentBlock.getHash().substring(0, difficulty).contentEquals(hashTarget))
			{
				System.out.println("Block has not been mined");
				return false;
			}
			
		}
		return true;
	}

}
