grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
		mavenRepo 'http://files.couchbase.com/maven2/'
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
		compile 'org.apache.tomcat:tomcat-catalina:8.0.15'
		
        compile 'de.javakaffee.msm:memcached-session-manager:1.8.3', {
			excludes 'CouchbaseMock', 'annotations', 'catalina', 'catalina-ha', 'couchbase-client', 'coyote',
			         'hibernate-annotations', 'hibernate-core', 'hsqldb', 'httpclient', 'javassist',
			         'jettison', 'jmemcached-core', 'jsr305', 'mockito-core', 'netty', 'servlet-api',
			         'slf4j-simple', 'spymemcached', 'testng'
		}

		compile 'de.javakaffee.msm:memcached-session-manager-tc7:1.8.3', {
			excludes 'CouchbaseMock', 'annotations', 'hibernate-annotations', 'hibernate-core', 'hsqldb',
			         'httpclient', 'javassist', 'jettison', 'jmemcached-core', 'jsr305', 'memcached-session-manager',
			         'mockito-core', 'netty', 'slf4j-simple', 'testng', 'tomcat-catalina','tomcat-catalina-ha',
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