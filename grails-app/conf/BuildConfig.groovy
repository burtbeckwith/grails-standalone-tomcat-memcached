grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.repos.peya.url = "http://54.209.219.49:8081/nexus/content/repositories/thirdparty/"
grails.project.repos.peya.type = "maven"
grails.project.repos.peya.username = "deployment"
grails.project.repos.peya.password = "peya"
grails.project.repos.default = "peya"

grails.project.fork = [
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {
    inherits("global") {
    }
    log "warn"
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
		mavenRepo 'http://files.couchbase.com/maven2/'
    }
    dependencies {
        compile 'de.javakaffee.msm:memcached-session-manager-tc8:1.8.3', {
			excludes 'CouchbaseMock', 'annotations', 'catalina', 'catalina-ha', 'couchbase-client', 'coyote',
			         'hibernate-annotations', 'hibernate-core', 'hsqldb', 'httpclient', 'javassist',
			         'jettison', 'jmemcached-core', 'jsr305', 'mockito-core', 'netty', 'servlet-api',
			         'slf4j-simple', 'spymemcached', 'testng'
		}

		runtime 'net.spy:spymemcached:2.11.5', {
			excludes 'log4j', 'spring-beans'
		}

		runtime 'com.couchbase.client:couchbase-client:1.4.6', {
			excludes 'commons-codec', 'httpcore', 'httpcore-nio', 'jettison', 'netty', 'spymemcached'
		}

		runtime 'org.codehaus.jettison:jettison:1.3.7', {
			excludes 'junit', 'stax-api', 'wstx-asl'
		}

		runtime 'org.apache.httpcomponents:httpcore:4.4', {
			excludes 'junit', 'mockito-core'
		}

		runtime 'org.apache.httpcomponents:httpcore-nio:4.4', {
			excludes 'commons-logging', 'httpcore', 'junit', 'mockito-core'
		}

		runtime 'io.netty:netty-all:4.0.25.Final', {
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