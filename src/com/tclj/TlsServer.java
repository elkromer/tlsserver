package com.tclj;
import ipworks.*;

import java.util.Map;
import java.io.IOException;
import java.util.HashMap;

public class TlsServer implements IpdaemonEventListener {
    private Ipdaemon daemon;
    private Map<String, byte[]> inputBuffers;

    public TlsServer() {
        daemon = new Ipdaemon();
        inputBuffers = new HashMap<String, byte[]>();
        try {
            daemon.addIpdaemonEventListener(this);
        } catch (java.util.TooManyListenersException e) {
            throw new RuntimeException("Too many listeners. " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Initialization failed. " + e.getMessage());
        }
    }

    public void StartListening() {
        try {
            //daemon.setSSLCert(new Certificate(Certificate.cstPFXFile,"C:\\temp\\keys\\100.pfx", "test", "*"));
            //daemon.setSSLEnabled(true);
            daemon.setLocalPort(22223);
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
                // System.out.println("Connected client list ===========");
                // for (String keys : inputBuffers.keySet())
                // {
                //     System.out.println(keys);
                // }
                // System.out.println("========== ===========");
                // java.lang.Thread.sleep(1000);
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
        byte[] data = ipdaemonDataInEvent.text;
        byte[] odata = inputBuffers.get(ipdaemonDataInEvent.connectionId);
        
        if (odata == null) {
            inputBuffers.put(ipdaemonDataInEvent.connectionId, data);
        } else {
            // data already in the buffer
            int newlength = data.length + odata.length;
            byte[] newdata = new byte[newlength];
            // append it
            System.arraycopy(odata, 0, newdata, 0, odata.length);
            System.arraycopy(data, 0, newdata, odata.length, data.length);
            // update the buffer in the map
            // try {
            //     java.nio.file.Files.write(java.nio.file.Paths.get("C:\\temp\\tlsserver\\users\\" +ipdaemonDataInEvent.connectionId+ "\\sentdata.txt"), newdata);
            // } catch (IOException e) {
            //     System.out.println("IOException: " + e.getMessage());
            // }
            inputBuffers.put(ipdaemonDataInEvent.connectionId, newdata);
        }
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
