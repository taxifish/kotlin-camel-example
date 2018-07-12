package org.taxifish.web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

public class App {
    public static void main(String[] args) {
        Web3j w3 = Web3j.build(new HttpService("http://104.40.193.0:8545"));

        w3.web3ClientVersion().observable().subscribe( x -> {
            System.out.println("--------------> " + x.getWeb3ClientVersion());
        });

        w3.ethGetBalance("0xf07c7AA48a542F5032ae757b11F8c323673dA6B0", DefaultBlockParameterName.LATEST).observable().subscribe( x -> {
            System.out.println("Balance = " + x.getBalance());
        });


        Subscription subscription = w3.blockObservable(true).subscribe( block -> {
            System.out.println(block.getBlock().getNumber());
        });

    }
}
