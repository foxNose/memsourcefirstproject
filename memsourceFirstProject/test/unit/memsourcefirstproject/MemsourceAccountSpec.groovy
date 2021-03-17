package memsourcefirstproject

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MemsourceAccount)
@Mock(MemsourceToken)
class MemsourceAccountSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "validate MemsourceAccount sunny day"() {
        def account = new MemsourceAccount()
        account.username = "user"
        account.password = "password"
        
        account.validate()

        expect:
            !account.hasErrors()
    }

    void "save MemsourceAccount sunny day"() {
        def account = new MemsourceAccount()
        account.username = "user"
        account.password = "password"
        
        account.save()

        expect:
            !account.hasErrors() && MemsourceAccount.count() == 1
    }
    
    void "after save MemsourceAccount sunny day"() {
        def account = new MemsourceAccount()
        account.username = "user"
        account.password = "password"
        
        account.save()

        def a = MemsourceAccount.first()
        
        expect:
            a.username == "user" && a.password == "password"
    }

    void "create 2 MemsourceAccounts sunny day"() {
        def a1 = new MemsourceAccount()
        a1.username = "user"
        a1.password = "password"
        
        a1.save()
        
        def a2 = new MemsourceAccount()
        a2.username = "user2"
        a2.password = "password2"
        
        a2.save()

        expect:
            !a1.hasErrors() && !a2.hasErrors() && MemsourceAccount.count() == 2
    }

    void "validate MemsourceAccount empty username should fail"() {
        def account = new MemsourceAccount()
        account.password = "password"
        
        account.validate()

        expect:
            account.hasErrors()
    }

    void "validate MemsourceAccount empty password should fail"() {
        def account = new MemsourceAccount()
        account.username = "user"
        
        account.validate()

        expect:
            account.hasErrors()
    }

    void "validate MemsourceAccount empty username and password should fail"() {
        def account = new MemsourceAccount()
        
        account.validate()

        expect:
            account.hasErrors()
    }

    void "save MemsourceAccount empty username should fail"() {
        def account = new MemsourceAccount()
        account.password = "password"
        
        account.save()

        expect:
            account.hasErrors() && MemsourceAccount.count() == 0
    }

    void "save MemsourceAccount empty password should fail"() {
        def account = new MemsourceAccount()
        account.username = "user"
        
        account.save()

        expect:
            account.hasErrors() && MemsourceAccount.count() == 0
    }

    void "save MemsourceAccount empty username and password should fail"() {
        def account = new MemsourceAccount()
        
        account.save()

        expect:
            account.hasErrors() && MemsourceAccount.count() == 0
    }

    void "create MemsourceAccount with token sunny day"() {
        def account = new MemsourceAccount()
        account.username = "user"
        account.password = "password"
        
        account.save()
        
        def token = new MemsourceToken()
        token.token = "sdfjsdlkfjlsddsf45d6f4s6d5f4s6df4"
        token.expires = new Date()
        token.userId = 546466
        token.uid = "sdfsdfsdfsdfdsfsdf"
        token.account = account
        
        account.token = token
        account.save()
        token.save()

        expect:
            !account.hasErrors() && !token.hasErrors() && MemsourceAccount.count() == 1 && MemsourceToken.count() == 1
    }
}
