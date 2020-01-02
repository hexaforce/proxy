package io.hexaforce.squid;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.SecureRandom;

import javax.net.ssl.SSLSocket;

public class SocketSample3 implements Config {

	public static void main(String[] args) throws IOException {
		new SocketSample().execute();
	}


	final public static String API_LOGIN = "https://pg6kinl38e.execute-api.ap-northeast-1.amazonaws.com/api/login";

	void execute() throws IOException {

		SSLSocket socket = (SSLSocket) CLIENT_AUTH_SOCKET_FACTORY( //
				loadKeyManager(), //
				unCheckTrustManager(), //
				new SecureRandom() //
		).createSocket( //
				PROXY_HOST, //
				HTTPS_PROXY_PORT //
		);

		socket.addHandshakeCompletedListener(DEBUG_LISTENER());
		socket.startHandshake();

		PrintWriter request = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
		request.println("GET " + API_LOGIN + " HTTP/1.0");
		request.println();
		request.flush();

		System.out.println("test");
		dump_response(socket.getInputStream());

	}

}
