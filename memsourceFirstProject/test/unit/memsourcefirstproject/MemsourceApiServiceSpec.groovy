package memsourcefirstproject

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MemsourceApiService)
@Mock([MemsourceToken, MemsourceAccount])
class MemsourceApiServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }
    
    void "test dateToString sunny day"() {
        def date = service.dateFromString("2021-03-15T15:36:13+0000")
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        expect:
            cal.get(Calendar.YEAR) == 2021 && cal.get(Calendar.MONTH) == 2 && cal.get(Calendar.DAY_OF_MONTH) == 15 && cal.get(Calendar.HOUR_OF_DAY) == 16 && cal.get(Calendar.MINUTE) == 36 && cal.get(Calendar.SECOND) == 13
    }

    void "test isTokenExpired expired sunny day"() {
        def token = new MemsourceToken()
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, -1)
        
        expect:
            service.isTokenExpired(token)            
    }

    void "test isTokenExpired not expired sunny day"() {
        def token = new MemsourceToken()
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 1)
        token.expires = cal.getTime()

        expect:
            !service.isTokenExpired(token)            
    }

    void "test isLoggedIn user logged in"() {
        def token = new MemsourceToken()
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 1)
        token.expires = cal.getTime()

        def account = new MemsourceAccount()
        account.token = token

        expect:
            service.isLoggedIn(account)
    }

    void "test isLoggedIn user not logged in expired token"() {
        def token = new MemsourceToken()
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, -1)
        token.expires = cal.getTime()

        def account = new MemsourceAccount()
        account.token = token

        expect:
            !service.isLoggedIn(account)
    }

    void "test isLoggedIn user not logged in no token"() {
        def account = new MemsourceAccount()

        expect:
            !service.isLoggedIn(account)
    }

    void "test deleteToken account has token"() {
        def token = new MemsourceToken()
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, -1)
        token.expires = cal.getTime()
        token.token = "sdfsdf"
        token.uid = "sdfsdf"
        token.userId = 121321
        token.save()

        def account = new MemsourceAccount()
        account.username = "sdfaf"
        account.password = "sdfsdfsd"
        account.token = token
        account.save()

        service.deleteToken(account)

        expect:
            MemsourceToken.count() == 0 && MemsourceAccount.count == 1 && MemsourceAccount.first().token == null && account.token == null
    }

    void "test deleteToken account has no token"() {
        def account = new MemsourceAccount()
        account.username = "sdfaf"
        account.password = "sdfsdfsd"
        account.save()

        service.deleteToken(account)

        expect:
            MemsourceAccount.count == 1 && MemsourceAccount.first().token == null && account.token == null
    }

}
