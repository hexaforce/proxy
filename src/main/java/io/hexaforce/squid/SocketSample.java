package io.hexaforce.squid;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SocketSample implements Config {

	public static void main(String[] args) throws IOException {
		new SocketSample().execute();
	}

	void execute() throws IOException {

		// クライアント証明(自己証明)
		SSLSocketFactory clientAuthSocketFactory = CLIENT_AUTH_SOCKET_FACTORY();

		// クライアント証明 HTTPSプロクシのソケット
		SSLSocket socket = (SSLSocket) clientAuthSocketFactory.createSocket( //
				HTTPS_PROXY_SOCKET(), //
				PROXY_HOST, //
				HTTPS_PROXY_PORT, //
				true);

		socket.startHandshake();

		PrintWriter request = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
		request.println("GET " + API_LOGIN + " HTTP/1.0");
		request.println();
		request.flush();

		System.out.println("test");
		dump_response(socket.getInputStream());

	}

}
