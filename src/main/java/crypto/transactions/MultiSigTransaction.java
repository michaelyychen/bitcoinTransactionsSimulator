package crypto.transactions;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.io.File;

import static org.bitcoinj.script.ScriptOpCodes.*;

/**
 * Created by bbuenz on 24.09.15.
 */
public class MultiSigTransaction extends ScriptTransaction {

    private DeterministicKey bankKey;
    private DeterministicKey customerKey1;
    private DeterministicKey customerKey2;
    private DeterministicKey customerKey3;


    public MultiSigTransaction(NetworkParameters parameters, File file, String password) {
        super(parameters, file, password);
        bankKey = getWallet().freshReceiveKey();
        customerKey1 = getWallet().freshReceiveKey();
        customerKey2 = getWallet().freshReceiveKey();
        customerKey3 = getWallet().freshReceiveKey();

    }

    @Override
    public Script createInputScript() {
        // TODO: Create a script that can be spend using signatures from the bank and one of the customers

        ScriptBuilder builder = new ScriptBuilder();
        builder.smallNum(1);
        builder.data(customerKey1.getPubKey());
        builder.data(customerKey2.getPubKey());
        builder.data(customerKey3.getPubKey());
        builder.smallNum(3);
        builder.op(OP_CHECKMULTISIG);
        builder.op(OP_IF);
        builder.data(bankKey.getPubKey());
        builder.op(OP_CHECKSIG);
        builder.op(OP_ENDIF);

        return builder.build();
    }



    @Override
    public Script createRedemptionScript(Transaction unsignedTransaction) {
        // Please be aware of the CHECK_MULTISIG bug!

        TransactionSignature txSigBank = sign(unsignedTransaction, bankKey);
        TransactionSignature txSigCust1 = sign(unsignedTransaction, customerKey1);
        TransactionSignature txSigCust2 = sign(unsignedTransaction, customerKey2);
        TransactionSignature txSigCust3 = sign(unsignedTransaction, customerKey3);

        ScriptBuilder builder = new ScriptBuilder();
        builder.data(txSigBank.encodeToBitcoin());
        builder.smallNum(0);                             // Padding for CHECK_MULTISIG bug
        builder.data(txSigCust3.encodeToBitcoin());

        return builder.build();
    }


}
