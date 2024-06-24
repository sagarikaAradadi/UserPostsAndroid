package com.pudagane.mobiquity.test.ui.details

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.pudagane.mobiquity.test.R
import com.pudagane.mobiquity.test.adapter.CityAdapter
import com.pudagane.mobiquity.test.model.City
import kotlinx.android.synthetic.main.fragment_city_list.*

class CityListFragment : Fragment() {

    private lateinit var cityListViewModel: CityListViewModel
    private lateinit var adapter: CityAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cityListViewModel =
                ViewModelProvider(this).get(CityListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_city_list, container, false)
//        val textView: TextView = root.findViewById(R.id.text_notifications)
//        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        cityListViewModel.getCities()?.observe(viewLifecycleOwner, Observer<List<City>> { this.renderMessges(it) })

        return root
    }

    private fun renderMessges(cities: List<City>?){
        adapter = context?.let { CityAdapter(cityListViewModel,cities) }!!
        val layoutManager = LinearLayoutManager(context)
        recyclerviewCities.layoutManager = layoutManager
        recyclerviewCities.adapter = adapter
        adapter.onItemClick = { cityName ->

            Log.d("TAG", cityName)
            val bundle = Bundle()
            bundle.putString("CITY_NAME", cityName)
            Navigation.findNavController(requireView()).navigate(R.id.navigation_city_forecast, bundle)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when(item.itemId) {
            R.id.add_location -> {
                Navigation.findNavController(requireView()).navigate(R.id.navigation_select_location_from_mag_fragment)
                true
            }
            R.id.add_help->{
                Navigation.findNavController(requireView()).navigate(R.id.navigation_help)
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        })
    }
}