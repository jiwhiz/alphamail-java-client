AlphaMail Java Client
========

This is a clone of [Comfirm AplhaMail J2EE Client](https://github.com/comfirm/alphamail-j2ee-client) project. Just re-organize file structure and add Maven pom.xml.

Add dependency to your Maven project pom.xml:

	<repositories>
	...
		<repository>
			<id>jiwhiz.maven</id>
			<name>Jiwhiz Maven Repository</name>
			<url>https://github.com/jiwhiz/maven/raw/master/</url>
		</repository>
	</repositories>
	...
	<dependencies>
	...
		<dependency>
			<groupId>com.comfirm.alphamail</groupId>
			<artifactId>alphamail-java-client</artifactId>
			<version>0.1.0</version>
		</dependency>
	</dependencies>

See my blog post [Send Email Through AlphaMail Inside CloudFoundry](http://www.jiwhiz.com/post/2012/11/Send_Email_Through_AlphaMail_Inside_CloudFoundry) for exemplary usage.
