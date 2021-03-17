package memsourcefirstproject

import grails.transaction.Transactional

@Transactional
class SetupController {
    
    def memsourceApiService
    def userService
    
    /**
     * First empty view / Edit view
     */
    def index() { 
        def account = userService.getCurrentUser()
        if (account == null) {
            account = new MemsourceAccount()
        }
        
        [account:account]
    }
    

    /**
     * Save acount info action
     */
    def save(String username, String password) {
        if (userService.login(username, password, flash)) {
            redirect controller: 'projects'
            return
        }

        redirect action: 'index'
    }

    /**
     * Logout action
     */
    def logout() {
        userService.logout()
        redirect action: 'index'
    }

}
