# Bitcoin Transactions Simulator
This simulator create transactions and post them to the Bitcoin blockchain on either mainnet or testnet. Each Bitcoin transaction is built programmatically by specifying the scripts in the specific subclasses. 

## Overview
In the <i>transactions</i> package you'll find 4 classes implementing different Bitcoin transactions.
1. PayToPubKey - standard P2PK transaction with specifying the output public address with proper signature.
2. PayToPubKeyHash - standard P2PKH transaction with specifying the hash of output public address.
3. LinearEquationTransaction - nonstandard transaction which can only be redeeemed by the solution of the linear equation solution
4. MutiSigTransaction - requires 1-outof-3 signature and an middleman to redeem the transaction

## How to Run it 
1. Generate Bitcoin wallet address by running <i>printAddress()</i> in the unit test.
2. Specify the wallet address manually in the test class.
3. Go to https://testnet.manu.backend.hamburg/faucet to acquire some testnet Bitcoin
4. Run the simulator test in JUnit

## For More Details on How Bitcoin Transactions Work
- https://en.bitcoin.it/wiki/Transaction
- https://d28rh4a8wq0iu5.cloudfront.net/bitcointech/readings/princeton_bitcoin_book.pdf?a=1 [Chapter 3]
