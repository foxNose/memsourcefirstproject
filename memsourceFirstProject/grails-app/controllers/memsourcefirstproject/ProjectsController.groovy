package memsourcefirstproject

import grails.transaction.Transactional

@Transactional
class ProjectsController {

    def memsourceApiService
    def userService

    def index() {
        MemsourceAccount account = userService.getCurrentUser()
        if (account == null){
            redirect controller: 'setup', action: 'index'
        }
    }
    
    def projects(int page) {
        MemsourceAccount account = userService.getCurrentUser()
        def response = new ProjectsResponse()
        if (account != null){
            response = memsourceApiService.listProjects(account, page)
        }      

        respond response
    }

}