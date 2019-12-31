package io.hexaforce.squid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public interface Config {

	final public static String PROXY_HOST = "squid.hexaforce.io";
	final public static int HTTP_PROXY_PORT = 3128;
	final public static int HTTPS_PROXY_PORT = 443;

	final public static Proxy HTTP_PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, HTTP_PROXY_PORT));
	final public static Proxy HTTPS_PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, HTTPS_PROXY_PORT));

	default Socket HTTPS_PROXY_SOCKET() {
		try {
			return new Socket(PROXY_HOST, HTTPS_PROXY_PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// final public static File KEY_STORE_FILE = new File(".keystore");
	final public static File KEY_STORE_FILE = new File("squidCA.keystore");

	final public static char[] KEY_STORE_PASSWORD = "B9cMw7qX".toCharArray();

	default SSLSocketFactory CLIENT_AUTH_SOCKET_FACTORY() throws IOException {
		try {
			// SSLContext.getDefault();
			SSLContext context = SSLContext.getInstance("TLS");
			// KeyManagerFactory.getDefaultAlgorithm();
			KeyManagerFactory factory = KeyManagerFactory.getInstance("SunX509");
			try {
				// KeyStore.getDefaultType();
				KeyStore store = KeyStore.getInstance("JKS");
				try {
					store.load(new FileInputStream(KEY_STORE_FILE), KEY_STORE_PASSWORD);
					try {
						factory.init(store, KEY_STORE_PASSWORD);
						try {
							context.init(factory.getKeyManagers(), null, null);
							return context.getSocketFactory();
						} catch (KeyManagementException e) {
							e.printStackTrace();
						}
					} catch (UnrecoverableKeyException e) {
						e.printStackTrace();
					}
				} catch (CertificateException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (KeyStoreException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	default SSLSocketFactory UNCHECK_CLIENT_AUTH_SOCKET_FACTORY() throws IOException {

		SSLContext context;
		try {
			context = SSLContext.getInstance("TLS");
			TrustManager[] unChecklientTrust = { new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					System.out.println("############### getAcceptedIssuers ###############");
					return new X509Certificate[0];
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
					System.out.println("############### checkClientTrusted ###############");
					System.out.println("authType :" + authType);
					dump_certs(certs);
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
					System.out.println("############### checkServerTrusted ###############");
					System.out.println("authType :" + authType);
					dump_certs(certs);
				}

				private void dump_certs(X509Certificate[] certs) {
					for (X509Certificate c : certs) {
						System.out.println("SigAlgName :" + c.getSigAlgName());
						// System.out.println("PublicKey :" + c.getPublicKey());
						try {
							dump(c.getIssuerAlternativeNames());
							dump(c.getSubjectAlternativeNames());
						} catch (CertificateParsingException e) {
							e.printStackTrace();
						}
					}
					System.out.println("　");
				}

				private void dump(Collection<List<?>> c) {
					if (c != null) {
						for (List<?> o : c) {
							if (o != null) {
								System.out.println("Issuer :" + o.toString());
							}
						}
					}
				}
			} };
			try {
				context.init(null, unChecklientTrust, new SecureRandom());
				return context.getSocketFactory();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	final public static String API_LOGIN = "https://pg6kinl38e.execute-api.ap-northeast-1.amazonaws.com/api/login";

	default URL API_URL_LOGIN() {
		try {
			return new URL(API_LOGIN);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	default void dump_response(InputStream inputstream) {
		BufferedReader response = new BufferedReader(new InputStreamReader(inputstream));
		String inputLine;
		try {
			while ((inputLine = response.readLine()) != null)
				System.out.println(inputLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
