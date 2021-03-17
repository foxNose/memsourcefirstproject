package memsourcefirstproject

class MemsourceToken {

    String token
    Date expires
    BigInteger userId
    String uid

    static belongsTo = [account: MemsourceAccount]

    static constraints = {
        
    }
}
