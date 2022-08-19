package es.adrianfg.comprasfamiliares.data.response

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class UserResponse {
    var email: String? = ""
    var pass: String? = ""
    var name: String? = ""
    var surName: String? = ""
    var age: Int? = -1

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

}