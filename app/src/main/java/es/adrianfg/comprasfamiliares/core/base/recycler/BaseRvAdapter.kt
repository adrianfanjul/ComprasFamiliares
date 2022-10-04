package es.adrianfg.comprasfamiliares.core.base.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import es.adrianfg.comprasfamiliares.BR
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.extension.autoNotify
import kotlin.properties.Delegates

typealias OnClickItem<T> = ((T?) -> Unit)

class BaseRvAdapter<T : Any>(
    private val dataview: Int,
    items: List<T>? = emptyList(),
    private val viewmodel: BaseViewModel? = null,
    private val itemClick: OnClickItem<T>,

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<T> by Delegates.observable(items ?: emptyList()) { _, old, new ->
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
            holder.binding.setVariable(BR.adapter, this)
            holder.binding.setVariable(BR.position, position)
            holder.bind(items[position])
        }
    }

    fun listenerItemBtnClick(position: Int){
        itemClick(items[position])
    }



}

