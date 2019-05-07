package id.test.kostbenhil.shared.supportclasses

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import id.test.kostbenhil.R
import kotlinx.android.synthetic.main.layout_nav_bottom.view.*


class NavBottomKos: ConstraintLayout, View.OnClickListener {

    private val TAG = NavBottomKos::class.java.simpleName

    enum class MENU {
        HOME, PENYEWA, KEUANGAN, PENGATURAN
    }

    /**
     * Variables
     */
    //State
    private lateinit var mContext: Context
    private lateinit var viewContent: View

    private var listener: NavBottomListener? = null

    /**
     * Constructors
     */
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    /**
     * Initialization
     */
    private fun init(context: Context, attributeSet: AttributeSet?) {
        mContext = context

        //Inflate view
        viewContent = LayoutInflater.from(mContext).inflate(R.layout.layout_nav_bottom, this, true)

        // set selected
        viewContent.imgHome.isSelected = true
        // set onclick
        viewContent.imgHome.setOnClickListener(this)
        viewContent.imgNotification.setOnClickListener(this)
        viewContent.imgTransaction.setOnClickListener(this)
        viewContent.imgOther.setOnClickListener(this)
    }

    fun setOnNavbarClick(navBottomListener: NavBottomListener) {
        listener = navBottomListener
    }

    override fun onClick(v: View?) {
        when(v) {
            viewContent.imgHome -> listener?.onClickHome()
            viewContent.imgTransaction -> listener?.onClickHistory()
            viewContent.imgNotification -> listener?.onClickNotification()
            viewContent.imgOther -> listener?.onClickOther()
        }
        selectedMenu(v)
    }

    fun setSelectedMenu(selected: MENU) {
        when(selected) {
            MENU.HOME -> onClick(viewContent.imgHome)
            MENU.PENYEWA -> onClick(viewContent.imgTransaction)
            MENU.KEUANGAN -> onClick(viewContent.imgNotification)
            MENU.PENGATURAN -> onClick(viewContent.imgOther)
        }
    }

    fun getSelectedMenu(): MENU {
        return when{
            viewContent.imgHome.isSelected -> MENU.HOME
            viewContent.imgTransaction.isSelected -> MENU.PENYEWA
            viewContent.imgNotification.isSelected -> MENU.KEUANGAN
            viewContent.imgOther.isSelected -> MENU.PENGATURAN
            else -> MENU.HOME
        }
    }

    private fun selectedMenu(v: View?) {
        when(v) {
            viewContent.imgHome -> {
                viewContent.imgHome.isSelected = true
                viewContent.imgTransaction.isSelected = !viewContent.imgHome.isSelected
                viewContent.imgNotification.isSelected = !viewContent.imgHome.isSelected
                viewContent.imgOther.isSelected = !viewContent.imgHome.isSelected
            }
            viewContent.imgTransaction -> {
                viewContent.imgTransaction.isSelected = true
                viewContent.imgHome.isSelected = !viewContent.imgTransaction.isSelected
                viewContent.imgNotification.isSelected = !viewContent.imgTransaction.isSelected
                viewContent.imgOther.isSelected = !viewContent.imgTransaction.isSelected
            }
            viewContent.imgNotification -> {
                viewContent.imgNotification.isSelected = true
                viewContent.imgHome.isSelected = !viewContent.imgNotification.isSelected
                viewContent.imgTransaction.isSelected = !viewContent.imgNotification.isSelected
                viewContent.imgOther.isSelected = !viewContent.imgNotification.isSelected
            }
            viewContent.imgOther -> {
                viewContent.imgOther.isSelected = true
                viewContent.imgHome.isSelected = !viewContent.imgOther.isSelected
                viewContent.imgNotification.isSelected = !viewContent.imgOther.isSelected
                viewContent.imgTransaction.isSelected = !viewContent.imgOther.isSelected
            }
        }
    }

    interface NavBottomListener {
        fun onClickHome()
        fun onClickNotification()
        fun onClickHistory()
        fun onClickOther()
    }
}