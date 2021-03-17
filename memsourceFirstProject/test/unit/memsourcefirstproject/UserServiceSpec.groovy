package memsourcefirstproject

import grails.test.mixin.TestFor
import spock.lang.Specification

import javax.servlet.http.HttpSession

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@Mock(MemsourceAccount)
@TestFor(UserService)
class UserServiceSpec extends Specification {

    def setup() {
        def session = new MockSession()
        service.metaClass.getSession = {-> return session.asType(HttpSession) }
    }

    def cleanup() {
    }

    void "test getCurrentUser logged in"() {
        def session = service.getSession()

        def account = new MemsourceAccount()
        account.username = "sdfsd"
        account.password = "sdfsdf"
        account.save()

        session.userId = account.id

        def a = service.getCurrentUser()

        expect:
            MemsourceAccount.count() == 1 && a != null && a.id == account.id && a.username == account.username && a.password == account.password
    }

    void "test getCurrentUser not logged in"() {
        def session = service.getSession()

        session.userId = null

        def a = service.getCurrentUser()
        
        expect:
            MemsourceAccount.count() == 0 && a == null
    }
}
