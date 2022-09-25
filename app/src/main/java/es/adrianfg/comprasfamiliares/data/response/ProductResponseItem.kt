package es.adrianfg.comprasfamiliares.data.response

import com.google.firebase.database.IgnoreExtraProperties
import es.adrianfg.comprasfamiliares.domain.models.User

@IgnoreExtraProperties
class ProductResponseItem {
    var name: String? = ""
    var description: String? = ""
    var amount:Int ?=-1
    var image: String? = ""
    var user:String?=""

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    constructor(name: String?, description: String?,amount:Int?, image: String?, user:  String?) {
        this.name = name
        this.description = description
        this.amount = amount
        this.image = image
        this.user = user
    }
}