package crypto.transactions;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import java.io.File;
import java.math.BigInteger;

import static org.bitcoinj.script.ScriptOpCodes.*;

/**
 * Created by bbuenz on 24.09.15.
 */

public class LinearEquationTransaction extends ScriptTransaction {
    private DeterministicKey key;

    public LinearEquationTransaction(NetworkParameters parameters, File file, String password) {
        super(parameters, file, password);
        key = getWallet().freshReceiveKey();
    }

    @Override
    public Script createInputScript() {
        // TODO: Create a script that can be spend by two numbers x and y such that x+y=first 4 digits of your suid and |x-y|=last 4 digits of your suid (perhaps +1)

        ScriptBuilder builder = new ScriptBuilder();

        builder.op(OP_2DUP);
        builder.op(OP_ADD);
        builder.data(encode(BigInteger.valueOf(1452)));
        builder.op(OP_SUB);
        builder.op(OP_SUB);
        builder.op(OP_SUB);
        builder.data(encode(BigInteger.valueOf(8704)));
        builder.op(OP_EQUAL);

        return builder.build();

    }

    @Override
    public Script createRedemptionScript(Transaction unsignedScript) {

        ScriptBuilder builder = new ScriptBuilder();
        builder.data(encode(BigInteger.valueOf(5078)));       //input X
        builder.data(encode(BigInteger.valueOf(-3626)));      //input Y

        return builder.build();

    }

    private byte[] encode(BigInteger bigInteger) {
        return Utils.reverseBytes(Utils.encodeMPI(bigInteger, false));
    }
}
