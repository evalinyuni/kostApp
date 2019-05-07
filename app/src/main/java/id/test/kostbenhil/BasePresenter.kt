package id.test.kostbenhil

interface BasePresenter<in T : BaseView> {

    fun onAttach(view: T)

    fun onDetach()
}