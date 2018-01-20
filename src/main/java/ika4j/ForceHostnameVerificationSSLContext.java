package ika4j;

import javax.net.ssl.*;
import java.security.*;
import java.util.List;

/**
 * Glorious hack to always create an SSL Engine with a hostname and port.
 * engineGetServerSessionContext and engineGetClientSessionContext can't be implemented without violating class/module loading constraints,
 * but for some reason, they are not being called.
 * @author Jochen Bedersdorfer
 * @see <a href="https://gist.github.com/beders/51d3600d7fb57ad7d36a1745749ef641">ForceHostnameVerificationSSLContext.java</a>
 * @see <a href="https://stackoverflow.com/questions/44479678/enable-tls-sni-in-new-java-9-client">Enable TLS SNI in new Java 9 client</a>
 * */
class ForceHostnameVerificationSSLContext extends SSLContext {
    private String hostname;
    ForceHostnameVerificationSSLContext(String hostname, int port) {
        super(new ForcedSSLContextSpi(hostname, port), null, "Default");
        this.hostname = hostname;
    }

    SSLParameters getParametersForSNI() {
        SSLParameters params = null;
        try {
            params = SSLContext.getDefault().getDefaultSSLParameters();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //noinspection ConstantConditions
        params.setServerNames(List.of(new SNIHostName(hostname)));
        params.setEndpointIdentificationAlgorithm("HTTPS");
        return params;
    }

    public static class ForcedSSLContextSpi extends SSLContextSpi {

        private final String hostname;
        private final int port;

        ForcedSSLContextSpi(String hostname, int port) {
            this.hostname = hostname;
            this.port = port;
        }

        @Override
        protected void engineInit(KeyManager[] keyManagers, TrustManager[] trustManagers, SecureRandom secureRandom) {

        }

        @Override
        protected SSLSocketFactory engineGetSocketFactory() {
            try {
                return SSLContext.getDefault().getSocketFactory();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected SSLServerSocketFactory engineGetServerSocketFactory() {
            try {
                return SSLContext.getDefault().getServerSocketFactory();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected SSLEngine engineCreateSSLEngine() {
            try {
                return SSLContext.getDefault().createSSLEngine(hostname, port);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected SSLEngine engineCreateSSLEngine(String host, int port) {
            try {
                return SSLContext.getDefault().createSSLEngine(host, port);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected SSLSessionContext engineGetServerSessionContext() {
            return null;
        }

        @Override
        protected SSLSessionContext engineGetClientSessionContext() {
            return null;
        }
    }
}
