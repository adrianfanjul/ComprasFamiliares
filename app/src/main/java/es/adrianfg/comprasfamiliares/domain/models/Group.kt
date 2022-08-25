package es.adrianfg.comprasfamiliares.domain.models

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(val name:String,val description:String,val image:String,val users:List<User>): Parcelable