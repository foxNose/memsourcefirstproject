package memsourcefirstproject

import grails.transaction.Transactional
import groovy.json.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.DeserializationFeature

@Transactional
class MemsourceApiService {
    
    def rest
    def grailsApplication

    def login(MemsourceAccount account) {
        if (account.token != null && isTokenExpired(account.token)){
            deleteToken(account)
            log.info "Successfully purged expired token for user ${account.username}"
        }

        LoginRequest loginRequest = new LoginRequest()
        loginRequest.userName = account.username
        loginRequest.password = account.password

        def resp = rest.post("${grailsApplication.config.memsource.api.login.url}") {
            contentType "application/json; charset=UTF-8"
            accept "application/json"
            json({loginRequest})
        }

        if(resp.status == 200) {
            // If account exists -> merge
            if (!account.id) {
                def a = MemsourceAccount.findByUsername(account.username)
                if (a != null) {
                    account.id = a.id
                    a.password = account.password
                    account = a
                }
                else {
                    account.save(flush:true)
                }
            }

            if (account.token == null) {
                account.token = new MemsourceToken()
            }

            account.token.token = resp.json.token
            account.token.expires = dateFromString(resp.json.expires)
            account.token.userId = new BigInteger(resp.json.user.id)
            account.token.uid = resp.json.user.uid
            account.save(flush:true)

            log.info "Successfully logged in user ${account.username} (expires: ${account.token.expires})"
            return true
        }
        else {
            log.error "Could not complete login request to Memsource API. Recieved response with status code ${resp.status}"
            log.debug resp.text
            return false
        }
    }

    def logout(MemsourceAccount account) {
        if (account.token == null || account.token.token == null) {
            log.error "Could not logout user ${account.username} without token!"
            return
        }

        if (isTokenExpired(account.token)){
            deleteToken(account)
            log.info "Successfully purged expired token for user ${account.username}. Skipping logout..."
            return
        }

        MemsourceToken token = account.token

        def resp = rest.post("${grailsApplication.config.memsource.api.logout.url}?token=${token.token}") {
            contentType "application/json; charset=UTF-8"
            accept "application/json"
        }

        if(resp.status == 204) {
            deleteToken(account)
            log.info "Successfully logged off user ${account.username}"
        }
        else {
            log.error "Could not complete logout request to Memsource API. Recieved response with status code ${resp.getStatusCode()}"
            log.debug resp.text
        }
    }

    def deleteToken(MemsourceAccount account) {
        if (account.token == null) {
            return
        }
        def token = account.token
        account.token = null
        account.save(flush:true)
        token.delete(flush:true)
    }

    def listProjects(MemsourceAccount account, Integer page) {
        if (!isLoggedIn(account)){
            login(account)
        }

        def pageSize = grailsApplication.config.memsource.api.pageSize
        def resp = rest.get(
            "${grailsApplication.config.memsource.api.listprojects.url}"
                + "?token=${account.token.token}&pageSize=${pageSize}&pageNumber=${page}") { 
            contentType "application/json; charset=UTF-8"
            accept "application/json"
        }

        if(resp.status == 200) {
            def mapper = new ObjectMapper()
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            ProjectsResponse projects = mapper.readValue(resp.json.toString(), ProjectsResponse.class)
            return projects
        }
        else {
            log.error "Could not complete list projects request to Memsource API. Recieved response with status code ${resp.status}"
            log.debug resp
            log.debug resp.text
            return null
        }
    }

    def isLoggedIn(MemsourceAccount account) {
        return account.token != null && !isTokenExpired(account.token)
    }

    def isTokenExpired(MemsourceToken token) {
        return token.expires < new Date()
    } 

    def dateFromString(String dateString) {
        String format = "yyyy-MM-dd'T'hh:mm:ssZ"   
        return Date.parse(format, dateString)
    }

}
