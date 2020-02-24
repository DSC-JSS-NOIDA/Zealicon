package tronku.project.zealicon.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.search_fragment.*
import tronku.project.zealicon.Adapter.CategoryAdapter
import tronku.project.zealicon.Model.CategoryModel

import tronku.project.zealicon.R
import tronku.project.zealicon.Viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel by lazy { SearchViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val categories: ArrayList<CategoryModel> = ArrayList()
        categories.add(CategoryModel(1, "Colorodo", R.drawable.ic_guitar, R.drawable.curved_card_black, R.color.blue_black_darker))
        categories.add(CategoryModel(2, "Mechavoltz", R.drawable.ic_violin, R.drawable.curved_card_blue, R.color.light_blue_900))
        categories.add(CategoryModel(3, "Play-it-on", R.drawable.ic_saxophone, R.drawable.curved_card_brown, R.color.brown_900))
        categories.add(CategoryModel(4, "Robotiles", R.drawable.ic_drumset, R.drawable.curved_card_red, R.color.red_900))
        categories.add(CategoryModel(5, "Coderz", R.drawable.ic_piano, R.drawable.curved_card_purple, R.color.purple_900))
        categories.add(CategoryModel(6, "Z-Wars", R.drawable.ic_accordion, R.drawable.curved_card_green, R.color.green_900))

        val categoryAdapter = CategoryAdapter(categories)
        categoryRecyclerView.adapter = categoryAdapter
    }

}
