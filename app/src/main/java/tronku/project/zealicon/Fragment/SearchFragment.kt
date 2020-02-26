package tronku.project.zealicon.Fragment

import android.os.Bundle
import android.os.PatternMatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_play_list.*
import kotlinx.android.synthetic.main.item_category.*
import kotlinx.android.synthetic.main.search_fragment.*
import tronku.project.zealicon.Adapter.*
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.CategoryModel
import tronku.project.zealicon.Model.EventTrackDB
import tronku.project.zealicon.R
import tronku.project.zealicon.Viewmodel.PlaylistViewModel
import tronku.project.zealicon.Viewmodel.SearchViewModel
import java.util.*
import java.util.Locale.filter
import java.util.function.Predicate
import kotlin.collections.ArrayList


class SearchFragment : Fragment(), CategoryViewHolder.onClickListener {

    private val viewModel by lazy { SearchViewModel() }
    private val db by lazy { RoomDB(context!!) }
    private val adapter = SearchAdapter(SearchTarget.PLAYLIST)
    private var eventCategory: String? = null


    companion object {
        fun newInstance() = SearchFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addCategoryCards()
        searchTextChangeListner()
        cancelFilter.setOnClickListener {
            filterCategoryText.text = ""
            clipLinearLayout.visibility = View.GONE
            categoryLayout.visibility = View.VISIBLE
            eventCategory = null
        }

        setObserver()
        viewModel.getPlaylist(db)

    }

    private fun setObserver() {
        searchRecyclerView.adapter = adapter
        viewModel.playlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.isNullOrEmpty()) {
                Toast.makeText(context, "Something went wrong! Try again.", Toast.LENGTH_SHORT).show()
            } else {
                adapter.submitList(it)
            }
        })
    }

    private fun searchTextChangeListner() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {

                if (editable.toString().isNotEmpty()) {
                    cancelSearch.visibility = View.VISIBLE
                    searchRecyclerView.visibility = View.VISIBLE
                    viewModel.playlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                        if (it.isNullOrEmpty()) {
                            Toast.makeText(context, "Something went wrong! Try again.", Toast.LENGTH_SHORT).show()
                        } else {
                            if (eventCategory.isNullOrBlank())
                                adapter.submitList(it
                                    .filter{(EventTrackDB, name) ->
                                        name!!.contains(editable.toString(), ignoreCase = true)})
                            else
                                adapter.submitList(it
                                    .filter{(EventTrackDB, name,a,b,c, category) ->
                                        name!!.contains(editable.toString(), ignoreCase = true) &&
                                            category!!.contains(eventCategory.toString(), ignoreCase = true)}
//                                .filter { (EventTrackDB, category) ->
//                                    category!!.contains(eventCategory.toString(), ignoreCase = true) }
//
                                    )
                            adapter.notifyDataSetChanged()
                        }
                    })

                } else {
                    searchRecyclerView.visibility = View.GONE
                    cancelSearch.visibility = View.GONE
                }
            }
        })
    }

    private fun addCategoryCards() {
        val categories: ArrayList<CategoryModel> = ArrayList()
        categories.add(
            CategoryModel(
                1,
                "Colorodo",
                R.drawable.ic_guitar,
                R.drawable.curved_card_black,
                R.color.blue_black_darker
            )
        )
        categories.add(
            CategoryModel(
                2,
                "Mechavoltz",
                R.drawable.ic_violin,
                R.drawable.curved_card_blue,
                R.color.light_blue_900
            )
        )
        categories.add(
            CategoryModel(
                3,
                "Play-it-on",
                R.drawable.ic_saxophone,
                R.drawable.curved_card_brown,
                R.color.brown_900
            )
        )
        categories.add(
            CategoryModel(
                4,
                "Robotiles",
                R.drawable.ic_drumset,
                R.drawable.curved_card_red,
                R.color.red_900
            )
        )
        categories.add(
            CategoryModel(
                5,
                "Coderz",
                R.drawable.ic_piano,
                R.drawable.curved_card_purple,
                R.color.purple_900
            )
        )
        categories.add(
            CategoryModel(
                6,
                "Z-Wars",
                R.drawable.ic_accordion,
                R.drawable.curved_card_green,
                R.color.green_900
            )
        )

        val categoryAdapter = CategoryAdapter(categories, this)
        categoryRecyclerView.adapter = categoryAdapter

    }

    override fun onClick(category: String?) {
//        viewModel.playlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//
//        })
        eventCategory = category
        filterCategoryText.text = eventCategory
        clipLinearLayout.visibility = View.VISIBLE
        searchEditText.requestFocus()
    }

}
