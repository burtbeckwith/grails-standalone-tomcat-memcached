grails.project.work.dir = 'target'
grails.project.docs.output.dir = 'docs/manual' // for backwards-compatibility, the docs are checked into gh-pages branch

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenRepo 'http://files.couchbase.com/maven2/'
		mavenLocal()
		mavenCentral()
	}

	dependencies {
		
		compile 'de.javakaffee.msm:memcached-session-manager:1.8.3', {
			excludes 'CouchbaseMock', 'annotations', 'catalina', 'catalina-ha', 'couchbase-client', 'coyote',
			         'hibernate-annotations', 'hibernate-core', 'hsqldb', 'httpclient', 'javassist',
			         'jettison', 'jmemcached-core', 'jsr305', 'mockito-core', 'netty', 'servlet-api',
			         'slf4j-simple', 'spymemcached', 'testng'
		}

		compile 'de.javakaffee.msm:memcached-session-manager-tc7:1.8.3', {
			excludes 'CouchbaseMock', 'annotations', 'hibernate-annotations', 'hibernate-core', 'hsqldb',
			         'httpclient', 'javassist', 'jettison', 'jmemcached-core', 'jsr305', 'memcached-session-manager',
			         'mockito-core', 'netty', 'slf4j-simple', 'testng', 'tomcat-catalina', 'tomcat-catalina-ha',
			         'tomcat-coyote', 'tomcat-servlet-api'
		}

		runtime 'net.spy:spymemcached:2.11.4', {
			excludes 'log4j', 'spring-beans'
		}

		runtime 'com.couchbase.client:couchbase-client:1.4.5', {
			excludes 'commons-codec', 'httpcore', 'httpcore-nio', 'jettison', 'netty', 'spymemcached'
		}

		runtime 'org.codehaus.jettison:jettison:1.3.6', {
			excludes 'junit', 'stax-api', 'wstx-asl'
		}

		runtime 'org.apache.httpcomponents:httpcore:4.3.3', {
			excludes 'junit', 'mockito-core'
		}

		runtime 'org.apache.httpcomponents:httpcore-nio:4.3.3', {
			excludes 'commons-logging', 'httpcore', 'junit', 'mockito-core'
		}

		runtime 'io.netty:netty:3.9.5.Final', {
			excludes 'activation', 'commons-logging', 'easymock', 'easymockclassextension', 'jboss-logging-spi',
			         'jboss-marshalling', 'jboss-marshalling-river', 'jboss-marshalling-serial', 'junit', 'log4j',
			         'org.osgi.compendium', 'org.osgi.core', 'protobuf-java', 'servlet-api', 'slf4j-api', 'slf4j-simple'
		}
	}

	plugins {
		build(':release:3.0.1', ':rest-client-builder:2.0.3') {
			export = false
		}
	}
}
