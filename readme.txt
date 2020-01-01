# build
docker build . -t alpine/openjdk8

# run 
mkdir alpine
docker container run -it -v /Users/relics9/alpine:/tmp alpine/openjdk8 ash


keytool -genkey \
 -storepass B9cMw7qX \
 -dname "CN=squid.hexaforce.io, OU=development, O=Hexaforce, L=Adachi, ST=Tokyo, C=JP" \
 -keypass B9cMw7qX

keytool -import -trustcacerts \
 -storepass B9cMw7qX \
 -alias squidCA2 \
 -file squidCA2.cert


java InstallCert squid.hexaforce.io:443


keytool -exportcert -alias squid.hexaforce.io-1 -keystore jssecacerts -storepass changeit -file squid.hexaforce.io.cert

keytool -list -keystore 


keytool -import -trustcacerts \
 -storepass B9cMw7qX \
 -alias squid.hexaforce.io \
 -file squid.hexaforce.io.cert

keytool -importcert \
 -storepass B9cMw7qX \
 -alias squid.hexaforce.io \
 -file squid.hexaforce.io.cert
 
ip.addr==13.113.169.93
