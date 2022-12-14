package es.adrianfg.comprasfamiliares.domain.models

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val email:String,val pass:String,val name:String, val surName:String, val age:Int): Parcelable