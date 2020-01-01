package io.hexaforce.squid;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SocketSample3 implements Config {

	public static void main(String[] args) throws IOException {
		new SocketSample().execute();
	}

	void execute() throws IOException {

		SSLSocketFactory clientAuthSocketFactory = CLIENT_AUTH_SOCKET_FACTORY();
		SSLSocket tunnel = (SSLSocket) clientAuthSocketFactory.createSocket(PROXY_HOST, HTTPS_PROXY_PORT);

//		OutputStream out = tunnel.getOutputStream();
//		InputStream in = tunnel.getInputStream();

//		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//
//		SSLSocket socket = (SSLSocket) clientAuthSocketFactory.createSocket( //
//				tunnel, //
//				PROXY_HOST, //
//				HTTPS_PROXY_PORT, //
//				true);
//
//		socket.startHandshake();

		PrintWriter request = new PrintWriter(new BufferedWriter(new OutputStreamWriter(tunnel.getOutputStream())));
		request.println("GET " + API_LOGIN + " HTTP/1.0");
		request.println();
		request.flush();

		System.out.println("test");
		dump_response(tunnel.getInputStream());
		
	}

}
