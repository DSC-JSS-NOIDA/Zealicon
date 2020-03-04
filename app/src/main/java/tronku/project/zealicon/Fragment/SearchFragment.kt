package tronku.project.zealicon.Fragment

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.search_fragment.*
import tronku.project.zealicon.Adapter.*
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.CategoryModel
import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.ExtraUtils
import tronku.project.zealicon.Viewmodel.SearchViewModel
import kotlin.collections.ArrayList


class SearchFragment : Fragment(), CategoryViewHolder.OnClickListener {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ExtraUtils.hideKeyboard(context as Activity)
        addCategoryCards()
        searchTextChangeListner()

        cancelFilter.setOnClickListener {
            if (!searchEditText.text.toString().isNotEmpty())
                searchRecyclerView.visibility = View.GONE
            filterCategoryText.text = ""
            clipLinearLayout.visibility = View.GONE
            categoryLayout.visibility = View.VISIBLE
            eventCategory = null
            viewModel.playlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if (it.isNullOrEmpty()) {
                    Toast.makeText(context, "Something went wrong! Try again.", Toast.LENGTH_SHORT).show()
                } else {
                    if (searchEditText.text.toString().isNotEmpty())
                        adapter.submitList(it
                            .filter{(EventTrackDB, name,a,b,c, category) ->
                                name!!.contains(searchEditText.text.toString(), ignoreCase = true)})
                    else
                        adapter.submitList(it)
                    adapter.notifyDataSetChanged()
                }
            })
        }

        cancelSearch.setOnClickListener {
            searchEditText.setText("")
            if (clipLinearLayout.visibility == View.GONE)
                    searchRecyclerView.visibility = View.GONE
            else{
                viewModel.playlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    if (it.isNullOrEmpty()) {
                        Toast.makeText(context, "Something went wrong! Try again.", Toast.LENGTH_SHORT).show()
                    } else {
                        adapter.submitList(it
                            .filter{(EventTrackDB, name,a,b,c, category) ->
                                        category!!.contains(eventCategory.toString(), ignoreCase = true)})
                        adapter.notifyDataSetChanged()
                    }
                })
            }
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
                                            category!!.contains(eventCategory.toString(), ignoreCase = true)})
                            adapter.notifyDataSetChanged()
                        }
                    })

                } else {
                    if (clipLinearLayout.visibility == View.GONE)
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
                "Colorado",
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
        eventCategory = category
        filterCategoryText.text = eventCategory
        searchRecyclerView.visibility = View.VISIBLE
        clipLinearLayout.visibility = View.VISIBLE
        searchEditText.requestFocus()
        viewModel.playlist.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.isNullOrEmpty()) {
                Toast.makeText(context, "Something went wrong! Try again.", Toast.LENGTH_SHORT).show()
            } else {
                    adapter.submitList(it
                        .filter{(EventTrackDB, name,a,b,c, category) ->
                            name!!.contains(searchEditText.text.toString(), ignoreCase = true) &&
                                    category!!.contains(eventCategory.toString(), ignoreCase = true)})
                adapter.notifyDataSetChanged()
            }
        })
    }

}
