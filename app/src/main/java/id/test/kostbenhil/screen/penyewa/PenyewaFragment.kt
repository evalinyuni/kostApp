package id.test.kostbenhil.screen.penyewa

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.test.kostbenhil.BaseAdapter

import id.test.kostbenhil.R
import id.test.kostbenhil.model.Penyewa
import id.test.kostbenhil.model.StatusResponse
import id.test.kostbenhil.shared.supportclasses.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.fragment_penyewa.view.*
import kotlinx.android.synthetic.main.layout_list_penyewa.*
import kotlinx.android.synthetic.main.layout_list_penyewa.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PenyewaFragment : Fragment(), PenyewaInterface, View.OnClickListener  {

    private var presenter: PenyewaPresenter? = null

    private lateinit var viewContainer: View
    private var isLoadingNextPage = false
    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener
    private lateinit var tenantAdapter: TenantAdapter
    var arrTenant: ArrayList<Penyewa> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewContainer = inflater.inflate(R.layout.fragment_penyewa, container, false)

        initView()
        return viewContainer
    }

    private fun initView() {
        viewContainer.swipeTenant.setSize(SwipeRefreshLayout.DEFAULT)
        viewContainer.swipeTenant.setColorSchemeResources(R.color.red)
        viewContainer.swipeTenant.setOnRefreshListener {
            isLoadingNextPage = true
            endlessRecyclerOnScrollListener.setCurrent_page(0)
            presenter!!.listTenant(endlessRecyclerOnScrollListener.getCurrent_page())
        }

        val linearLayoutManager = LinearLayoutManager(activity as Context)
        endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener(linearLayoutManager) {
            override fun onLoadMore(current_page: Int) {
                if (!isLoadingNextPage) {
                    if (!viewContainer.rvListTenant.isComputingLayout)
//                        tenantAdapter.showLoading()
                    presenter!!.listTenant(current_page)
                    isLoadingNextPage = true
                }
            }
        }
        endlessRecyclerOnScrollListener.setVisibleThreshold(2)

        viewContainer.rvListTenant.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            addOnScrollListener(endlessRecyclerOnScrollListener)
        }
    }

    override fun onClick(v: View?) {
    }

    override fun onAttach() {
        presenter = PenyewaPresenter(activity as Context)
        presenter?.onAttach(this)
    }

    fun refreshPage() {
        if (isAdded && !viewContainer.swipeTenant.isRefreshing) onAttach()
    }

    override fun onProgress() {
        viewContainer.swipeTenant.isRefreshing = true
    }

    override fun onFinishProgress() {
        viewContainer.swipeTenant.isRefreshing = false
    }


    override fun onSuccessListTenant(_arrListTenant: ArrayList<Penyewa>) {
        if (isAdded) {
            viewContainer.rvListTenant.visibility = View.VISIBLE
            viewContainer.rvListTenant.layoutManager = LinearLayoutManager(context as Activity)
            this.tenantAdapter = TenantAdapter(context as Activity, arrTenant)
            viewContainer.rvListTenant.adapter = this.tenantAdapter
//            if (endlessRecyclerOnScrollListener.getCurrent_page() == 0) {
//                tenantAdapter = TenantAdapter(context as Activity, penyewa)
//                tenantAdapter = TenantAdapter(activity as Context,
//                    penyewa.listAccountHistoryDetails,
//                    object : BaseAdapter.OnItemClickListener {
//                        override fun onItemClick(view: View, position: Int) {
//                            val accountHistoryDetails = accountHistory.listAccountHistoryDetails[position]
//                            val billerPayment = BillerPayment()
//                            billerPayment.transactionNumber = accountHistoryDetails.transactionNumber
//                            billerPayment.traceNumber = accountHistoryDetails.traceNumber
//                            val serviceDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
//                            val date = serviceDateFormat.parse(accountHistoryDetails.dateString)
//                            val dateFormatToShow = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
//                            val dateToShow = dateFormatToShow.format(date)
//                            billerPayment.transactionDate = dateToShow
//                            billerPayment.transactionStatus = accountHistoryDetails.status
//                            billerPayment.description = accountHistoryDetails.fields
//                            billerPayment.isNeedReceiptInquiry = accountHistoryDetails.needReceiptInquiry
//                            billerPayment.type = accountHistoryDetails.type
//                            startActivity(PurchaseView.newIntent(activity as Context, billerPayment, false))
//                        }
//                    })
//                viewContainer.rvListTenant.adapter = tenantAdapter
//                viewContainer.lblTransactionHistory.setVisible(accountHistory.listAccountHistoryDetails.size > 0 || isSetFilter)
//                viewContainer.btnFilterTransaction.setVisible(accountHistory.listAccountHistoryDetails.size > 0 || isSetFilter)
//                viewContainer.rvListTenant.setVisible(accountHistory.listAccountHistoryDetails.size > 0)
//                viewContainer.constNoHistoryTransaction.setVisible(accountHistory.listAccountHistoryDetails.size == 0)
//                viewContainer.divider.setVisible(accountHistory.listAccountHistoryDetails.size > 0)

//            } else {
//                tenantAdapter.hideLoading()
//                for (i in 0 until accountHistory.listAccountHistoryDetails.size) {
//                    val accountHistoryDetails = accountHistory.listAccountHistoryDetails[i]
//                    historyTransactionAdapter.add(accountHistoryDetails)
//                }
//            }
//            if (accountHistory.listAccountHistoryDetails.size == 10 /* page size 10 */) isLoadingNextPage = false
            onFinishProgress()
        }
    }

    override fun onFailed(statusResponse: StatusResponse) {
        if (isAdded) {
            if (statusResponse.message.isNotEmpty())
            isLoadingNextPage = false
            onFinishProgress()
        }    }

    companion object {
        fun newInstance(): PenyewaFragment {
            val fragment = PenyewaFragment()
            return fragment
        }
    }
}
