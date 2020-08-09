package com.tclj;
import ipworks.*;

import java.util.Dictionary;
import java.util.TooManyListenersException;

public class TlsServer implements IpdaemonEventListener {
    private Ipdaemon daemon;

    public TlsServer() {
        daemon = new Ipdaemon();
        try {
            daemon.addIpdaemonEventListener(this);
        } catch (TooManyListenersException e) {
            throw new RuntimeException("Too many listeners. " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Initialization failed. " + e.getMessage());
        }
    }

    public void StartListening() {
        try {
            daemon.setSSLCert(new Certificate(Certificate.cstPFXFile,"C:\\temp\\keys\\100.pfx", "test", "*"));
            daemon.setSSLEnabled(true);
            daemon.setLocalPort(2222);
            daemon.setListening(true);
        } catch (IPWorksException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Caught unknown error. " + e.getMessage());
        }
    }

    public void DoAllEvents() {
        while (true) {
            try {
                daemon.doEvents();
            } catch (IPWorksException e) {
                throw new RuntimeException(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException("Caught unknown error. " + e.getMessage());
            }
        }
    }

    @Override
    public void connected(IpdaemonConnectedEvent ipdaemonConnectedEvent) {
        new TclRoutines().CreateUserFilesystem(ipdaemonConnectedEvent.connectionId);
    }

    @Override
    public void connectionRequest(IpdaemonConnectionRequestEvent ipdaemonConnectionRequestEvent) {

    }

    @Override
    public void dataIn(IpdaemonDataInEvent ipdaemonDataInEvent) {

    }

    @Override
    public void disconnected(IpdaemonDisconnectedEvent ipdaemonDisconnectedEvent) {
        System.out.println("Client disconnected.");
    }

    @Override
    public void error(IpdaemonErrorEvent ipdaemonErrorEvent) {

    }

    @Override
    public void readyToSend(IpdaemonReadyToSendEvent ipdaemonReadyToSendEvent) {

    }

    @Override
    public void SSLClientAuthentication(IpdaemonSSLClientAuthenticationEvent ipdaemonSSLClientAuthenticationEvent) {

    }

    @Override
    public void SSLConnectionRequest(IpdaemonSSLConnectionRequestEvent ipdaemonSSLConnectionRequestEvent) {

    }

    @Override
    public void SSLStatus(IpdaemonSSLStatusEvent ipdaemonSSLStatusEvent) {

    }
}
