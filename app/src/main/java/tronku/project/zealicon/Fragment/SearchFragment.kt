package tronku.project.zealicon.Fragment

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.search_fragment.*
import tronku.project.zealicon.Activity.MainActivity
import tronku.project.zealicon.Adapter.CategoryAdapter
import tronku.project.zealicon.Model.CategoryModel

import tronku.project.zealicon.R
import tronku.project.zealicon.Utils.AnimUtils
import tronku.project.zealicon.Viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel

        val categories: ArrayList<CategoryModel> = ArrayList()
        categories.add(CategoryModel("Colaro", R.drawable.cultural_category, R.drawable.curved_card_yellow))
        categories.add(CategoryModel("Coderz", R.drawable.coder_category, R.drawable.curved_card_blue))
        categories.add(CategoryModel("Mechavoltz", R.drawable.mecha_category, R.drawable.curved_card_purple))
        categories.add(CategoryModel("Play it On", R.drawable.gaming_category, R.drawable.curved_card_red))
        categories.add(CategoryModel("Robotiles", R.drawable.robots_category, R.drawable.curved_card_blue_dark))
        categories.add(CategoryModel("Z-Wars", R.drawable.war_category, R.drawable.curved_card_green))

        val categoryAdapter: CategoryAdapter = CategoryAdapter(categories)
        categoryRecyclerView.adapter = categoryAdapter
//        categoryRecyclerView.a
    }

}
