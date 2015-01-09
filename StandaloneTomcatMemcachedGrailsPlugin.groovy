class StandaloneTomcatMemcachedGrailsPlugin {
    def version = "1.0.0-tc8"
    def grailsVersion = "2.3 > *"
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]
    def title = "Standalone Tomcat Memcached Plugin" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''
    def documentation = "http://grails.org/plugin/standalone-tomcat-memcached"
}