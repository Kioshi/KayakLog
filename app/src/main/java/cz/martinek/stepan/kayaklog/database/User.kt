package cz.martinek.stepan.kayaklog.database


class User {

    var id: Int = 0
    var username: String? = null

    constructor(id: Int, username: String) {
        this.id = id
        this.username = username
    }
    constructor(username: String) {
        this.username = username
    }
}