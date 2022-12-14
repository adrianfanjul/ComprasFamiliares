package es.adrianfg.comprasfamiliares.data.response

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class UserResponseItem {
    var email: String? = ""
    var pass: String? = ""
    var name: String? = ""
    var surName: String? = ""
    var age: Int? = -1

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    constructor(email: String?, pass: String?, name: String?, surName: String?, age: Int?) {
        this.email = email
        this.pass = pass
        this.name = name
        this.surName = surName
        this.age = age
    }

}