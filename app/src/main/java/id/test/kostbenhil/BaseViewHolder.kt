package id.test.kostbenhil
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<in T>(itemView: View, private val onClickListener: BaseAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    abstract fun bind(data: T)

    override fun onClick(v: View) = onClickListener.onItemClick(v, adapterPosition)
}