package com.tclj;

public class Main {


    public static void main(String[] args) {
        TlsServer server = new TlsServer();
        server.StartListening();
        server.DoAllEvents();
    }
}
