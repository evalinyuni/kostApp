package id.test.kostbenhil.screen.penyewa

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.test.kostbenhil.R
import id.test.kostbenhil.model.Penyewa
import kotlinx.android.synthetic.main.item_tenant.view.*

class TenantAdapter(private val context: Context,
                    private var tenantItem: ArrayList<Penyewa>
) : RecyclerView.Adapter<TenantAdapter.TenantHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantHolder = TenantHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_tenant,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: TenantHolder, position: Int) {
        holder.bindView(tenantItem[position])
    }


    override fun getItemCount(): Int {
        return tenantItem.size
    }

    inner class TenantHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(tenant: Penyewa) {
            itemView.tvRoom.text = tenant.arrTenants[0].id.toString()
            itemView.tvPrice.text = tenant.arrTenants[0].amount.toString()

//            for (_tenants in tenant.arrTenants) {
//
//            }
        }
    }

}