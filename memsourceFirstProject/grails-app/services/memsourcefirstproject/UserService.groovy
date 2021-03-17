package memsourcefirstproject

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.util.WebUtils
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Transactional
class UserService {

    def memsourceApiService
    def messageSource

    def getCurrentUser() {
        HttpSession session = getSession()

        def account = null

        if (session.userId) {
            account = MemsourceAccount.findById(session.userId)
        }
        return account
    }

    def login(String username, String password, def flash) {
        HttpSession session = getSession()
        def locale = Locale.getDefault()

        def currentAccount = getCurrentUser()
        def account = new MemsourceAccount()

        account.username = username 
        def currentPassword = (currentAccount == null)?"":currentAccount.password
        // Empty password => not changed || not filled by user
        account.password = (password.trim().isEmpty()) ? currentPassword : password 
        
        if (!account.validate()){           
            def errorMessages = new ArrayList<String>(account.errors.getFieldErrorCount())
            for (fieldErrors in account.errors.getAllErrors()) {
                for(error in fieldErrors){
                    errorMessages.add(messageSource.getMessage(error, locale))
                }
            }

            flash.message = messageSource.getMessage('pages.setup.error.updateerror', null, locale)
            flash.errors = errorMessages
            return false
        }

        if(!memsourceApiService.login(account)) {
            flash.message = messageSource.getMessage('pages.setup.error.invalidcredentials', null, locale)
            return false
        }

        session.userId = account.id

        flash.message = messageSource.getMessage('pages.setup.success.updateok', null, locale)
        return true
    }

    def logout() {
        HttpSession session = getSession()

        def account = getCurrentUser()
        memsourceApiService.logout(account)

        session.userId = null
    }

    def getSession() {
        HttpServletRequest request = WebUtils.retrieveGrailsWebRequest().currentRequest
        HttpSession session = request.session
        return session
    }
}
