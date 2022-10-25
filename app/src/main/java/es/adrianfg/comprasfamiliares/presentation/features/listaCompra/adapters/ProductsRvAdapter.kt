package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import es.adrianfg.comprasfamiliares.BR
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.glide.GlideApp
import es.adrianfg.comprasfamiliares.core.base.recycler.BaseViewHolderBinding
import es.adrianfg.comprasfamiliares.core.extension.autoNotify
import es.adrianfg.comprasfamiliares.domain.models.Product
import kotlin.properties.Delegates

typealias OnClickProductItem<T> = ((T?) -> Unit)

class ProductsRvAdapter<T : Any>(
    private val context:Context,
    private val dataview: Int,
    items: List<Product>? = emptyList(),
    private val viewmodel: BaseViewModel? = null,
    private val itemClick: OnClickProductItem<Product>

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Product> by Delegates.observable(items ?: emptyList()) { _, old, new ->
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
            GlideApp.with(context)
                .load(Firebase.storage.reference.child(items[position].image))
                .defaultOptions(context.getDrawable(R.drawable.img_sin_imagen))
                .into(holder.itemView.findViewById<ImageView>(R.id.item_product_img));
        }
    }

    fun listenerItemBtnClick(position: Int){
        try {
            itemClick(items[position])
        }catch (e:Exception){
            e.message?.let { Log.e("BaseRvAdapter", it) }
        }
    }

}

