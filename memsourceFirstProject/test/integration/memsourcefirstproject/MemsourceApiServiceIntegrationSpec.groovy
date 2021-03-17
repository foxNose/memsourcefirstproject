package memsourcefirstproject

import grails.test.spock.IntegrationSpec
import grails.test.mixin.integration.Integration
import grails.transaction.*
import org.codehaus.groovy.grails.web.util.WebUtils

@Integration
@Rollback
class MemsourceApiServiceIntegrationSpec extends IntegrationSpec {

    def grailsApplication
    def memsourceApiService

    def setup() {
    }

    def cleanup() {
    }

    void "test login with expired token"() {
        def account = new MemsourceAccount()
        account.username = grailsApplication.config.integrationTests.memsourceApi.credentials.username
        account.password = grailsApplication.config.integrationTests.memsourceApi.credentials.password

        memsourceApiService.login(account)

        account = MemsourceAccount.findById(account.id)

        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, -1)

        account.token.expires = cal.getTime()
        account.save(flush:true)

        expect:
            !account.token.hasErrors() && !account.hasErrors() 
        and: 
            memsourceApiService.login(account) && MemsourceAccount.count() == 1 && MemsourceToken.count() == 1 
        and:
            account.token.expires > cal.getTime()
    }

    void "test login with existing not expired token"() {

        def account = new MemsourceAccount()
        account.username = grailsApplication.config.integrationTests.memsourceApi.credentials.username
        account.password = grailsApplication.config.integrationTests.memsourceApi.credentials.password
        account.save(flush:true)

        expect: "first login is successful"
            !account.hasErrors() && memsourceApiService.login(account) 
        and: "all account relevant data are persisted"
            MemsourceAccount.count() == 1 && MemsourceToken.count() == 1 && account.token != null

        and: "second login is successful"
            memsourceApiService.login(account) && memsourceApiService.login(account)
        and: "all account relevant data are persisted after second login"
            MemsourceAccount.count() == 1 && MemsourceToken.count() == 1 && account.token != null
    }

    void "test login with ok credentials sunny day"() {
        def account = new MemsourceAccount()
        account.username = grailsApplication.config.integrationTests.memsourceApi.credentials.username
        account.password = grailsApplication.config.integrationTests.memsourceApi.credentials.password
        expect:
            memsourceApiService.login(account) 
        and:
            MemsourceAccount.count() == 1 && MemsourceToken.count() == 1
    }

    void "test login with not valid credentials should fail"() {
        def account = new MemsourceAccount()
        account.username = "not_valid_username"
        account.password = "not_valid_password"
        expect:
            !memsourceApiService.login(account)
        and:
            MemsourceAccount.count() == 0 && MemsourceToken.count() == 0
    }

    void "test list projects sunny day"() {
        def account = new MemsourceAccount()
        account.username = grailsApplication.config.integrationTests.memsourceApi.credentials.username
        account.password = grailsApplication.config.integrationTests.memsourceApi.credentials.password
        memsourceApiService.login(account)
        account = MemsourceAccount.findById(account.id)
        def projects = memsourceApiService.listProjects(account, 0)
        expect:
            MemsourceAccount.count() == 1 && MemsourceToken.count() == 1 
        and:
            projects != null && projects.pageNumber == 0
    }

    void "test list projects negative page number should return null"() {
        def account = new MemsourceAccount()
        account.username = grailsApplication.config.integrationTests.memsourceApi.credentials.username
        account.password = grailsApplication.config.integrationTests.memsourceApi.credentials.password
        memsourceApiService.login(account)
        def projects = memsourceApiService.listProjects(account, -1)
        expect:
            MemsourceAccount.count() == 1 && MemsourceToken.count() == 1 
        and:
            projects == null
    }

    void "test logout with expired token"() {
        def account = new MemsourceAccount()
        account.username = grailsApplication.config.integrationTests.memsourceApi.credentials.username
        account.password = grailsApplication.config.integrationTests.memsourceApi.credentials.password

        memsourceApiService.login(account)

        account = MemsourceAccount.findById(account.id)

        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, -1)

        account.token.expires = cal.getTime()

        account.save(flush:true)

        memsourceApiService.login(account)
        account = MemsourceAccount.findById(account.id)
        memsourceApiService.logout(account)

        expect:
            account.token == null
    }

    void "test logout with existing not expired token"() {
        def account = new MemsourceAccount()
        account.username = grailsApplication.config.integrationTests.memsourceApi.credentials.username
        account.password = grailsApplication.config.integrationTests.memsourceApi.credentials.password
        account.save(flush:true)
        memsourceApiService.login(account) 
        account = MemsourceAccount.findById(account.id)
        memsourceApiService.login(account)     
        account = MemsourceAccount.findById(account.id)
        memsourceApiService.logout(account)

        expect:
            account.token == null
    }
}
