package memsourcefirstproject

class MemsourceAccount {

    String username 
    String password

    static hasOne = [token: MemsourceToken]

    static constraints = { 
        username blank: false, nullable: false
        password blank: false, nullable: false
        token nullable: true
    }
}
