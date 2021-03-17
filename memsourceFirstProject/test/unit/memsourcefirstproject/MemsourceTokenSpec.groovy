package memsourcefirstproject

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MemsourceToken)
@Mock(MemsourceAccount)
class MemsourceTokenSpec extends Specification {

    def setup() {
        def account = new MemsourceAccount()
        account.username = "user"
        account.password = "password"
        
        account.save()
    }

    def cleanup() {
    }

    void "create MemsourceToken sunny day"() {
        def account = MemsourceAccount.first()
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

    void "validate MemsourceToken empty token should fail"() {
        def account = MemsourceAccount.first()
        def token = new MemsourceToken()
        
        token.expires = new Date()
        token.userId = 546466
        token.uid = "sdfsdfsdfsdfdsfsdf"
        token.account = account

        token.validate()

        expect:
            token.hasErrors()
    }

    void "validate MemsourceToken empty expires should fail"() {
        def account = MemsourceAccount.first()
        def token = new MemsourceToken()
        
        token.token = "sdfjsdlkfjlsddsf45d6f4s6d5f4s6df4"
        token.userId = 546466
        token.uid = "sdfsdfsdfsdfdsfsdf"
        token.account = account

        token.validate()

        expect:
            token.hasErrors()
    }

    void "validate MemsourceToken empty userId should fail"() {
        def account = MemsourceAccount.first()
        def token = new MemsourceToken()
        
        token.token = "sdfjsdlkfjlsddsf45d6f4s6d5f4s6df4"
        token.expires = new Date()
        token.uid = "sdfsdfsdfsdfdsfsdf"
        token.account = account

        token.validate()

        expect:
            token.hasErrors()
    }

    void "validate MemsourceToken empty uid should fail"() {
        def account = MemsourceAccount.first()
        def token = new MemsourceToken()
        
        token.token = "sdfjsdlkfjlsddsf45d6f4s6d5f4s6df4"
        token.expires = new Date()
        token.userId = 546466
        token.account = account

        token.validate()

        expect:
            token.hasErrors()
    }

    void "validate MemsourceToken empty account should fail"() {
        def token = new MemsourceToken()
        
        token.token = "sdfjsdlkfjlsddsf45d6f4s6d5f4s6df4"
        token.expires = new Date()
        token.userId = 546466
        token.uid = "sdfsdfsdfsdfdsfsdf"

        token.validate()

        expect:
            token.hasErrors()
    }

    void "save MemsourceToken empty attributes should fail"() {
        def token = new MemsourceToken()
        
        token.save()

        expect:
            token.hasErrors() && MemsourceToken.count() == 0
    }
}
