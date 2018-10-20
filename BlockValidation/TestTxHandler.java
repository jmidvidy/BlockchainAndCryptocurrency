package assignment1;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

public class TestTxHandler {
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
                String common = ""; //change back to assignment/testCases 1 before submitting
				String txPoolFile = common + "txPool.txt"; //need to change this back as well to without .txt extnesion
				String tx1File = common + "txValid";
				String tx2File = common + "txOutputNotInPool";
                String tx3File = common + "txWrongSig";
                String tx4File = common + "txMultipleTimes";
                String tx5File = common + "txNegativeOutput";
                String tx6File = common + "txOutputExceed";
				try{
					FileInputStream fi0 = new FileInputStream(txPoolFile);
					ObjectInputStream oi0 = new ObjectInputStream(fi0);
					FileInputStream fi1 = new FileInputStream(tx1File);
					ObjectInputStream oi1 = new ObjectInputStream(fi1);
					FileInputStream fi2 = new FileInputStream(tx2File);
					ObjectInputStream oi2 = new ObjectInputStream(fi2);
                    FileInputStream fi3 = new FileInputStream(tx3File);
                    ObjectInputStream oi3 = new ObjectInputStream(fi3);
                    FileInputStream fi4 = new FileInputStream(tx4File);
                    ObjectInputStream oi4 = new ObjectInputStream(fi4);
                    FileInputStream fi5 = new FileInputStream(tx5File);
                    ObjectInputStream oi5 = new ObjectInputStream(fi5);
                    FileInputStream fi6 = new FileInputStream(tx6File);
                    ObjectInputStream oi6 = new ObjectInputStream(fi6);
                    try{
						UTXOPool utxoPool = (UTXOPool) oi0.readObject();
						Transaction tx1 = (Transaction) oi1.readObject();
						Transaction tx2 = (Transaction) oi2.readObject();
						Transaction tx3 = (Transaction) oi3.readObject();
						Transaction tx4 = (Transaction) oi4.readObject();
						Transaction tx5 = (Transaction) oi5.readObject();
						Transaction tx6 = (Transaction) oi6.readObject();

						TxHandler student = new TxHandler(new UTXOPool(utxoPool));
						if (student.isValidTx(tx1)){
							System.out.println("Transaction 1: Valid. The correct answer should be true, your function returns True.");
						} else {
							System.out.println("Transaction 1: Valid. The correct answer should be true, your function returns False.");
						}
						if (!student.isValidTx(tx2)){
							System.out.println("Transaction 2: Output claimed are not in the current UTXO Pool. The correct answer should be false, your function returns False.");
						} else {
							System.out.println("Transaction 2: Output claimed are not in the current UTXO Pool. The correct answer should be false, your function returns True.");
						}
                        if (!student.isValidTx(tx3)){
							System.out.println("Transaction 3: Invalid signatures. The correct answer should be false, your function returns False.");
						} else {
							System.out.println("Transaction 3: Invalid signatures. The correct answer should be false, your function returns True.");
						}

                        if (!student.isValidTx(tx4)){
							System.out.println("Transaction 4: UXTO claimed multiple times. The correct answer should be false, your function returns False.");
						} else {
							System.out.println("Transaction 4: UXTO claimed multiple times. The correct answer should be false, your function returns True.");
						}
                        if (!student.isValidTx(tx5)){
							System.out.println("Transaction 5: Negative output. The correct answer should be false, your function returns False.");
						} else {
						    System.out.println("Transaction 5: Negative output. The correct answer should be false, your function returns True.");
						}
                        if (!student.isValidTx(tx6)){
							System.out.println("Transaction 6: Output greater than input. The correct answer should be false, your function returns False.");
						} else {
							System.out.println("Transaction 6: Output greater than input. The correct answer should be false, your function returns True.");
						}

					} catch (ClassNotFoundException ex){
						System.out.println(ex);
					}
					fi0.close();
					oi0.close();
					fi1.close();
					oi1.close();
					fi2.close();
					oi2.close();
                    fi3.close();
                    oi3.close();
                    fi4.close();
                    oi4.close();
                    fi5.close();
                    oi5.close();
                    fi6.close();
                    oi6.close();
				} catch (FileNotFoundException ex){
					System.out.println(ex);
				}
	}

}
