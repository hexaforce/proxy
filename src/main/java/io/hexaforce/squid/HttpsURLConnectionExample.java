package io.hexaforce.squid;

import java.io.IOException;
import java.net.Proxy;

import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionExample implements Config {

	public static void main(String[] args) throws IOException {

		HttpsURLConnectionExample example = new HttpsURLConnectionExample();

		/*********************
		 * CURLの場合 
PROXY_SERVER=http://squid.hexaforce.io:3128
TEST_API=https://pg6kinl38e.execute-api.ap-northeast-1.amazonaws.com/api/login
curl --cacert squidCA.pem $TEST_API -x $PROXY_SERVER -vvv
		 *********************/
		example.login(example.request(HTTP_PROXY));
		/*********************
		 * CURLの場合 
PROXY_SERVER=https://squid.hexaforce.io:443
TEST_API=https://pg6kinl38e.execute-api.ap-northeast-1.amazonaws.com/api/login
curl $TEST_API -x $PROXY_SERVER -vvv
		 *********************/
		example.login(example.request(HTTPS_PROXY));

	}

	private final static boolean validateClientTrust = false;

	private void login(HttpsURLConnection request) {

		System.err.println(" ");
		System.err.println(" ");

		try {

			if (validateClientTrust) {
				request.setSSLSocketFactory(CLIENT_AUTH_SOCKET_FACTORY());
			} else {
				request.setSSLSocketFactory(UNCHECK_CLIENT_AUTH_SOCKET_FACTORY());
			}
			System.out.println("test");
			dump_response(request.getInputStream());

		} catch (Exception e) {
			System.err.println("URL: " + request.getURL());
			System.err.println(e.getMessage());
		}

	}

	private HttpsURLConnection request(Proxy proxy) throws IOException {
		HttpsURLConnection connection = (HttpsURLConnection) API_URL_LOGIN().openConnection(proxy);
		connection.setRequestMethod("GET");
		connection.setInstanceFollowRedirects(false);
		return connection;
	}

	public int add(int a, int b) {
		return a + b;
	}

}
