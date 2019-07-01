package com.cuongz.notnewyorktimes.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.cuongz.notnewyorktimes.R
import com.cuongz.notnewyorktimes.articleSearch.ArticleSearchPresenter
import com.cuongz.notnewyorktimes.articleSearch.InterfaceArticleSearch
import com.cuongz.notnewyorktimes.model.Doc
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_filter_box.*


class MainActivity : AppCompatActivity(), InterfaceArticleSearch.View {

    private var presenter: InterfaceArticleSearch.Presenter = ArticleSearchPresenter(this)
    private var adapter: ArticleAdapter? = null
    private var _page = 0
    private var listOfDoc: MutableList<Doc> = ArrayList()
    private var beginDate: String? = null
    private var endDate: String? = null
    private var sort: String? = null
    private var art: String? = null
    private var fashion: String? = null
    private var sport: String? = null
    private var fq: String? = null
    private var q: String? = null
    private var isOpen = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set toolbar
        setSupportActionBar(toolbar)

        // set scrollListener for endless scroll
        newsList.layoutManager = LinearLayoutManager(this)
        adapter = ArticleAdapter(listOfDoc)
        newsList.adapter = adapter

        val scrollListener = object : EndlessRecyclerViewScrollListener(newsList.layoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Log.w("testing", "go here")
                Log.w("page", "$_page")
                presenter.searchNews(page, sort, fq, beginDate, endDate, q)
                this@MainActivity._page++
            }
        }

        newsList.addOnScrollListener(scrollListener)
        ////////////////////////////////////

        initProgressBar()

        presenter.searchNews(_page, sort, fq, beginDate, endDate, q)

        //set swipe refresh
        swipeContainer.setOnRefreshListener { fetchTimelineAsync() }

    }

    override fun onSetPresenter(presenter: InterfaceArticleSearch.Presenter) {
        this.presenter = presenter
    }

    override fun onSuccess(docs: List<Doc>) {
        this.listOfDoc.addAll(docs)
        adapter?.notifyDataSetChanged()


        swipeContainer.isRefreshing = false
        hideProgressBar()
    }


    override fun onFailure() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search View Hint"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                q = query
                listOfDoc.clear()
                presenter.searchNews(_page, sort, fq, beginDate, endDate, q)
                return false
            }

        })

        return true
    }

    fun filterNews(menuItem: MenuItem) {
        if (!isOpen) {
            item_filter_box.visibility = View.VISIBLE
            isOpen = true
        }
        else if(isOpen){
            item_filter_box.visibility = View.GONE
            isOpen = false
        }



        btn_ok.setOnClickListener {
            var x = false
            var y = false

            if (dayView.text == "00" || monthView.text == "00" || yearView.text == "0000") {

            } else {
                x = true
            }

            if (endDayView.text == "00" || endMonthView.text == "00" || endYearView.text == "0000") {

            } else {
                y = true
            }
            if (x && y) {
                if (sortSpinner.selectedItem.toString() == "Newest") {
                    sort = "newest"
                } else {
                    sort = "oldest"
                }


                if (checkbox_art.isChecked) {
                    art = "Art"
                }
                if (checkbox_fashion_style.isChecked) {
                    fashion = "Fashion"
                }
                if (checkbox_sport.isChecked) {
                    sport = "Sport"
                }
                fq = "$art $fashion $sport"

                beginDate = "${yearView.text}${monthView.text}${dayView.text}"
                endDate = "${endYearView.text}${endMonthView.text}${endDayView.text}"
                _page = 0
                listOfDoc.clear()
                presenter.searchNews(_page, sort, fq, beginDate, endDate, q)
                item_filter_box.visibility = View.GONE
            }
        }

        btn_reset.setOnClickListener {
            _page = 0
            beginDate = null
            endDate = null
            sort = null
            art = null
            fashion = null
            sport = null
            fq = null
            q = null
            dayView.text = getString(R.string._00)
            monthView.text = getString(R.string._00)
            yearView.text = getString(R.string._0000)
            endDayView.text = getString(R.string._00)
            endMonthView.text = getString(R.string._00)
            endYearView.text = getString(R.string._0000)
            checkbox_art.isChecked = false
            checkbox_fashion_style.isChecked = false
            checkbox_sport.isChecked = false

            listOfDoc.clear()
            presenter.searchNews(_page, sort, fq, beginDate, endDate, q)
        }

        btn_cancel.setOnClickListener {
            item_filter_box.visibility = View.GONE
        }
    }

    fun showDatePickerDialog(v: View) {
        val newFragment = BeginDatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun showEndDatePickerDialog(v: View) {
        val newFragment = EndDatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun initProgressBar() {
        progressBar.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun fetchTimelineAsync() {
        presenter.searchNews(_page, sort, fq, beginDate, endDate, q)
    }

}

