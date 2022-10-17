package es.adrianfg.comprasfamiliares.data.response

import com.google.firebase.database.IgnoreExtraProperties
import es.adrianfg.comprasfamiliares.domain.models.User

@IgnoreExtraProperties
class GroupResponseItem {
    var name: String? = ""
    var description: String? = ""
    var image: String? = ""
    var users: List<String>? = emptyList()
    var createUser:String ? = ""

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    constructor(name: String?, description: String?, image: String?, users:  List<String>?, createUser: String?) {
        this.name = name
        this.description = description
        this.image = image
        this.users = users
        this.createUser = createUser
    }
}