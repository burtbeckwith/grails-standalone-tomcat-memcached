import de.javakaffee.web.msm.MemcachedBackupSessionManager

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class StandaloneTomcatMemcachedGrailsPlugin {

	private final Logger log = LoggerFactory.getLogger('grails.plugin.standalone.StandaloneTomcatMemcachedGrailsPlugin')

	String version = '0.2'
	String grailsVersion = '2.0 > *'
	List pluginExcludes = [
		'docs/**',
		'src/docs/**'
	]

	String title = 'Standalone Tomcat Memcached Plugin'
	String author = 'Burt Beckwith'
	String authorEmail = 'burt@burtbeckwith.com'
	String description = 'Uses Memcached as the Tomcat session manager when using the Tomcat server'
	String documentation = 'http://grails.org/plugin/standalone-tomcat-memcached'

	String license = 'APACHE'
	def issueManagement = [system: 'GitHub', url: 'https://github.com/burtbeckwith/grails-standalone-tomcat-memcached/issues']
	def scm = [url: 'https://github.com/burtbeckwith/grails-standalone-tomcat-memcached']

	def doWithSpring = {
		def conf = application.config.grails.plugin.standalone.tomcat.memcached

		tomcatSessionManager(MemcachedBackupSessionManager) {
			enabled = (conf.enabled instanceof Boolean) ? conf.enabled : true
			enableStatistics = (conf.enableStatistics instanceof Boolean) ? conf.enableStatistics : true
			sessionBackupAsync = (conf.sessionBackupAsync instanceof Boolean) ? conf.sessionBackupAsync : false
			sticky = (conf.sticky instanceof Boolean) ? conf.sticky : false
			memcachedProtocol = conf.memcachedProtocol ?: 'binary'

			if (conf.sessionBackupTimeout instanceof Number) sessionBackupTimeout = conf.sessionBackupTimeout // 100
			if (conf.operationTimeout instanceof Number) operationTimeout = conf.operationTimeout // 1000
			if (conf.backupThreadCount instanceof Number) backupThreadCount = conf.backupThreadCount
			if (conf.copyCollectionsForSerialization instanceof Boolean) copyCollectionsForSerialization = conf.copyCollectionsForSerialization // false

			if (conf.username) username = conf.username
			if (conf.password) password = conf.password
			if (conf.failoverNodes) failoverNodes = conf.failoverNodes
			if (conf.lockingMode) lockingMode = conf.lockingMode
			if (conf.requestUriIgnorePattern) requestUriIgnorePattern = conf.requestUriIgnorePattern
			if (conf.sessionAttributeFilter) sessionAttributeFilter = conf.sessionAttributeFilter
			if (conf.transcoderFactoryClass) transcoderFactoryClass = conf.transcoderFactoryClass
			if (conf.customConverter) customConverter = conf.customConverter

			// must contain all memcached nodes you have running, or the membase bucket uri(s). It should be the same for all tomcats.
			// each node is defined as <id>:<host>:<port>. Several definitions are separated by space or comma (e.g. memcachedNodes="n1:app01:11211,n2:app02:11211")
			// For a single node the <id> is optional so that it's allowed to set only <host>:<port> (e.g. memcachedNodes="localhost:11211")
			// For usage with membase it's possible to specify one or more membase bucket uris, e.g.
			//    http://host1:8091/pools,http://host2:8091/pools. Bucket name and password are specified via the attributes username and password
			//   Connecting to membase buckets requires the binary memcached protocol
			memcachedNodes = conf.memcachedNodes ?: 'localhost:11211'
		}
	}

	def doWithApplicationContext = { ctx ->

		def tomcatSessionManager = ctx.tomcatSessionManager
		if (!tomcatSessionManager) {
			log.debug "No tomcatSessionManager bean found, not updating the Tomcat session manager"
			return
		}

		def servletContext = ctx.servletContext

		try {
			if ('org.apache.catalina.core.ApplicationContextFacade'.equals(servletContext.getClass().name)) {
				def realContext = servletContext.context
				if ('org.apache.catalina.core.ApplicationContext'.equals(realContext.getClass().name)) {
					def standardContext = realContext.@context
					if ('org.apache.catalina.core.StandardContext'.equals(standardContext.getClass().name)) {
						standardContext.manager = tomcatSessionManager
						log.info "Set the Tomcat session manager to $tomcatSessionManager"
					}
					else {
						log.warn "Not updating the Tomcat session manager, the context isn't an instance of org.apache.catalina.core.StandardContext"
					}
				}
				else {
					log.warn "Not updating the Tomcat session manager, the wrapped servlet context isn't an instance of org.apache.catalina.core.ApplicationContext"
				}
			}
			else {
				log.warn "Not updating the Tomcat session manager, the servlet context isn't an instance of org.apache.catalina.core.ApplicationContextFacade"
			}
		}
		catch (Throwable e) {
			log.error "There was a problem changing the Tomcat session manager: $e.message", e
		}
	}
}
