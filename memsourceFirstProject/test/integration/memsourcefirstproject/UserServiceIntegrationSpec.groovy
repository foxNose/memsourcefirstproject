package memsourcefirstproject

import grails.test.spock.IntegrationSpec
import grails.test.mixin.integration.Integration
import grails.transaction.*
import org.codehaus.groovy.grails.web.util.WebUtils

@Integration
@Rollback
class UserServiceIntegrationSpec extends IntegrationSpec {

    def grailsApplication
    def userService

    def setup() {
    }

    def cleanup() {
    }

    void "test login empty credentials should fail"() {
        def flash = [message:null, errors:null]
        def session = userService.getSession()
        
        expect:
            !userService.login("", "", flash) && flash.message != null && flash.errors != null && flash.errors.size() == 2
        and:
            session.userId == null
    }

    void "test login invalid credentials should fail"() {
        def flash = [message:null, errors:null]
        def session = userService.getSession()

        expect:
            !userService.login("not_valid_username", "not_valid_password", flash) 
        and:
            flash.message != null && flash.errors == null
        and:
            session.userId == null
    }

    void "test login ok credentials sunny day"() {
        def flash = [message:null, errors:null]
        def session = userService.getSession()

        expect:
            userService.login(
                grailsApplication.config.integrationTests.memsourceApi.credentials.username, 
                grailsApplication.config.integrationTests.memsourceApi.credentials.password, 
                flash) && flash.message != null && flash.errors == null && session.userId != null
    }

    void "test logout for logged in user sunny day"() {
        def flash = [message:null, errors:null]
        def session = userService.getSession()
        userService.login(
            grailsApplication.config.integrationTests.memsourceApi.credentials.username, 
            grailsApplication.config.integrationTests.memsourceApi.credentials.password, 
            flash) && flash.message != null && flash.errors == null && session.userId != null
        userService.logout()

        expect:
            session.userId == null
    }
}
