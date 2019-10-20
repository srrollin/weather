package dev.rollin.weather.view

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.rollin.weather.R
import dev.rollin.weather.constant.TempUnits
import dev.rollin.weather.model.Forecast
import dev.rollin.weather.util.LocationUtil
import dev.rollin.weather.viewmodel.CurrentLocationViewModel
import kotlinx.android.synthetic.main.current_location_fragment.*

class CurrentLocationFragment : Fragment()
{
    private var currentUnit = TempUnits.C
    private lateinit var locationUtil: LocationUtil
    private lateinit var currentLocation: Location
    private lateinit var adapter: DailyRecyclerViewAdapter

    companion object {
        fun newInstance() = CurrentLocationFragment()
    }

    private lateinit var viewModel: CurrentLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.current_location_fragment, container, false)

        var rvDaily = view.findViewById(R.id.rvDaily) as RecyclerView
        adapter = DailyRecyclerViewAdapter()
        rvDaily.layoutManager = LinearLayoutManager(context)
        rvDaily.adapter = adapter

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupLocationListeners()

        viewModel = ViewModelProviders.of(this).get(CurrentLocationViewModel::class.java)
        viewModel.forecast.observe(this,  Observer { bindView(it) })
    }

    override fun onResume() {
        super.onResume()
        locationUtil.startLocationUpdates()
    }

    private fun setupLocationListeners() {
        locationUtil = LocationUtil(requireActivity())
        locationUtil.startLocationUpdates()

        locationUtil.lastLocation.observe(this,  Observer { loadData(it) })

        locationUtil.currentLocation.observe(this,  Observer { loadData(it) })

        locationUtil.currentLocationAddress.observe(this, Observer {
            tvLocation.text = if (it[0].subLocality == null) it[0].adminArea else it[0].subLocality
        })
    }

    private fun bindView(forecast: Forecast) {
        tbFahrenheit.setOnClickListener { toggleUnit(TempUnits.F) }
        tbCelsius.setOnClickListener { toggleUnit(TempUnits.C) }

        forecast.currently?.run {
            tvTemp.text = temperature?.let { formatTemp(it) }
            tvFeelsLikeTemp.text = apparentTemperature?.let { formatTemp(it) }
            tvSummary.text = summary

            val url = "https://darksky.net/images/weather-icons/$icon.png"
            Picasso.get().load(url).into(ivIcon)
        }

        forecast.daily?.data?.run {
            val currentDay = firstOrNull()

            currentDay?.run {
                tvLowTemp.text = temperatureLow?.let { formatTemp(it) }
                tvHighTemp.text = temperatureHigh?.let { formatTemp(it) }
            }
            adapter.setItems(this)
        }

        if (forecast.latitude != null && forecast.longitude != null) {
            locationUtil.getAddressByCoordinates(forecast.latitude, forecast.longitude)
        }
    }

    private fun formatTemp(temperature: Double): String {
        return temperature.toInt().toString() + getUnit()
    }

    private fun getUnit(): String {
        return when(currentUnit) {
            TempUnits.C-> "\u2103"
            TempUnits.F-> "\u2109"
        }
    }

    private fun toggleUnit(unit: TempUnits) {
        currentUnit = unit
        when(unit) {
            TempUnits.C-> {
                tbCelsius.isChecked = true
                tbFahrenheit.isChecked = false
            }
            TempUnits.F-> {
                tbCelsius.isChecked = false
                tbFahrenheit.isChecked = true
            }
        }
        loadData(currentLocation)
    }

    private fun loadData(location: Location) {
        currentLocation = location
        viewModel.getForecast(location.latitude, location.longitude, currentUnit.unit)
    }
}
