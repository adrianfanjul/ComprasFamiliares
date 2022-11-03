package es.adrianfg.comprasfamiliares.presentation.features.groups.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import es.adrianfg.comprasfamiliares.BR
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.glide.GlideApp
import es.adrianfg.comprasfamiliares.core.base.recycler.BaseViewHolderBinding
import es.adrianfg.comprasfamiliares.core.extension.autoNotify
import es.adrianfg.comprasfamiliares.domain.models.Group
import kotlin.properties.Delegates


typealias OnClickGroupItem<T,Int> = ((T?,Int?) -> Unit)

class GroupsRvAdapter<T : Any>(
    private var logedUser:String,
    private val context:Context,
    private val dataview: Int,
    items: List<Group>? = emptyList(),
    private val viewmodel: BaseViewModel? = null,
    private val itemGroupClick: OnClickGroupItem<Group,Int>

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Group> by Delegates.observable(items ?: emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BaseViewHolderBinding(
            viewmodel,
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), dataview, parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseViewHolderBinding) {

            if(logedUser.equals(items[position].createUser)){
                holder.itemView.findViewById<MaterialButton>(R.id.item_group_delete_btn).visibility=View.VISIBLE
            }else{
                holder.itemView.findViewById<MaterialButton>(R.id.item_group_delete_btn).visibility=View.GONE
            }

            holder.binding.setVariable(BR.adapter, this)
            holder.binding.setVariable(BR.position, position)
            holder.bind(items[position])


            GlideApp.with(context)
                .load(Firebase.storage.reference.child(items[position].image))
                .defaultOptions(context.getDrawable(R.drawable.img_sin_imagen))
                .into(holder.itemView.findViewById(R.id.item_group_img));
        }
    }

    fun listenerItemBtnClick(position: Int,button:Int){
        try {
            itemGroupClick(items[position],button)
        }catch (e:Exception){
            e.message?.let { Log.e("GroupsRvAdapter", it) }
        }
    }

}

