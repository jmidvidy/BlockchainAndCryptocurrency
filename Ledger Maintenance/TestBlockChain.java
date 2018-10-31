package assignment2;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

public class TestBlockChain {
   public static void main(String[] args) throws FileNotFoundException,
			IOException {

String common = ""; //"assignment2/testCases//";

String[] description = {"Add a block with no transaction.","Add a block with many valid transactions.", "Add a block with an invalid preBlockHash.","Add a block with different kinds of invalid transactions.","Add a block containing a transaction that claims a UTXO not on its branch.","Process a linear chain of blocks with length less than CUT_OFF_AGE and then a block on top of the genesis block","Add a linear chain of blocks of length CUT_OFF_AGE + 1 and then a block on top of the genesis block."};

boolean[] testResult = {true, true, false, false, false, true,false};

for (int i = 0; i < 7; i++) {
	String chainFile = common + i + "blockChain";
	String blockFile = common + i + "block";
	try{
	FileInputStream fiChain = new FileInputStream(chainFile);
	ObjectInputStream oiChain = new ObjectInputStream(fiChain);
	FileInputStream fiBlock = new FileInputStream(blockFile);
	ObjectInputStream oiBlock = new ObjectInputStream(fiBlock);

	BlockChain blockChain = (BlockChain)oiChain.readObject();
	Block block = (Block)oiBlock.readObject();
	String printString = "---------------" + "\n" + "Test " + i + ": " + description[i] + "\n" + "The correct result should be " + testResult[i] + ", your result is " + blockChain.addBlock(block) + ".";
	System.out.println(printString);
	oiBlock.close();
	fiBlock.close();
	fiChain.close();
	oiChain.close();

} catch (Exception ex) {
	ex.printStackTrace();
}
}


}
}
