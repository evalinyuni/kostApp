package id.test.kostbenhil

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseAdapter<T, Holder : BaseViewHolder<T>>(private var data: MutableList<T>) : RecyclerView.Adapter<Holder>() {

    lateinit var itemClickListener: OnItemClickListener

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    abstract fun getItemResourceLayout(viewType: Int): Int
    abstract fun getItemViewTypeCustomMessage(data: T, position: Int): Int

    fun getView(parent: ViewGroup?, viewType: Int): View = LayoutInflater.from(parent?.context).inflate(getItemResourceLayout(viewType), parent, false)

    fun add(item: T) {
        data.add(item)
        notifyItemInserted(data.size.minus(1))
    }

    fun remove(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(data[position])

    override fun getItemViewType(position: Int): Int {
        return getItemViewTypeCustomMessage(data[position], position)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}