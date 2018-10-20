package assignment1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.security.PublicKey;


/**
 * EECS 395: Blockchain and Cryptocurrency
 * Fall 2018
 * Assignment1
 * 
 * @author Jeremy Midvidy, jam658
 *
 */


public class TxHandler {

    private UTXOPool utxoPool;

    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */
    public TxHandler(UTXOPool utxoPool) {
        this.utxoPool = new UTXOPool(utxoPool);
    }

    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool, [given]
     * (2) the signatures on each input of {@code tx} are valid, [working]
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and [done]
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     * values; and false otherwise. [working]
     */
    public boolean isValidTx(Transaction tx) {
        UTXOPool uniqueUtxos = new UTXOPool();
        double previousTxOutSum = 0;
        double currentTxOutSum = 0;
        
        // iterate through inputs for the given transaction to validate and test conditions
        for (int i = 0; i < tx.numInputs(); i++) {
        	
        	// Create new utxo for verification and gather mapped transaction
            Transaction.Input in = tx.getInput(i);
            UTXO utxo = new UTXO(in.prevTxHash, in.outputIndex);
            Transaction.Output out = utxoPool.getTxOutput(utxo);
            
            // T2 :: if the current utxo is not in the current UTXO pool --> false
            if (!utxoPool.contains(utxo)) 
            	return false; 
            
            // T3 :: invalid signature --> false
            PublicKey pK = out.address;
            byte[] m = tx.getRawDataToSign(i);
            byte[] sign = in.signature;
            if (!Crypto.verifySignature(pK, m, sign))
                return false;
            
            // T4 :: Double Spending --> if the current utxo is somewhere alredy in uniqueUtxo's, then you already 
            // 							 know that the current utxo is attempted to be doulbe spent
            if (uniqueUtxos.contains(utxo)) 
            	return false;
            
            // maintain uniqueUtxos and prevsums
            uniqueUtxos.addUTXO(utxo, out);
            previousTxOutSum += out.value;
        	}
            
        	// T5 :: Check to see if any output has a negative valid, if so --> false
            for (Transaction.Output out : tx.getOutputs()) {
            	if (out.value < 0) 
            		return false;
            	//maintain sum
                currentTxOutSum += out.value;
            }
            
            // T6 ::  Finally, OUTPUT must be greater than INPUT
            if (previousTxOutSum < currentTxOutSum)
            	return false;
            
            return true;
        
    }
    

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
    	
        Set<Transaction> validTxs = new HashSet<>();
        
        for (Transaction tx : possibleTxs) {
            if (isValidTx(tx)) { //<-- call to implement 
                validTxs.add(tx);
                for (Transaction.Input in : tx.getInputs()) {
                    UTXO utxo = new UTXO(in.prevTxHash, in.outputIndex);
                    utxoPool.removeUTXO(utxo);
                }
                for (int i = 0; i < tx.numOutputs(); i++) {
                    Transaction.Output out = tx.getOutput(i);
                    UTXO utxo = new UTXO(tx.getHash(), i);
                    utxoPool.addUTXO(utxo, out);
                }
            }
        }

        Transaction[] validTxArray = new Transaction[validTxs.size()];
        return validTxs.toArray(validTxArray);
    }
}