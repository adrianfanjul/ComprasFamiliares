package es.adrianfg.comprasfamiliares.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(val name:String,val description:String,val amount:Int,val image:String,val user:String,val group:String): Parcelable
