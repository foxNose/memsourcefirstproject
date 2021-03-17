package memsourcefirstproject

import grails.test.mixin.TestFor
import spock.lang.Specification

import javax.servlet.http.HttpSession

class MockSession implements HttpSession {
    def userId = null
    enum Fields {
        userId
    }

    def getAttribute(String name) {
        return userId
    }
    
    Enumeration<String> getAttributeNames() {
        return Fields
    }

    long getCreationTime() {
        return new Date().getTime()
    }

    String getId() {
        return "asdasd"
    }

    long getLastAccessedTime() {
        return new Date().getTime()
    }

    int getMaxInactiveInterval() {
        return 600
    }

    javax.servlet.ServletContext getServletContext() {
        return null
    }

    javax.servlet.http.HttpSessionContext getSessionContext() {
        return null
    }

    void putValue(String name, Object value) {
        userId = value
    }

    Object getValue(String name) {
        return userId
    }

    void invalidate() {}

    boolean isNew() {
        return false
    }

    void removeAttribute(String name) {}

    void setAttribute(String name, Object value) {
        userId = value
    }

    void setMaxInactiveInterval(int interval) {}

    void removeValue(String name) {}

    String[] getValueNames() {
        "userId"
    }
}