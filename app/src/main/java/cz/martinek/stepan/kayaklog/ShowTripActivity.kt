package cz.martinek.stepan.kayaklog

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import cz.martinek.stepan.kayaklog.model.Trip
import cz.martinek.stepan.kayaklog.model.TripFields
import cz.martinek.stepan.kayaklog.retrofit.API
import cz.martinek.stepan.kayaklog.retrofit.RedirectException
import cz.martinek.stepan.kayaklog.retrofit.ServerErrorException
import cz.martinek.stepan.kayaklog.retrofit.UnauthenticatedException
import kotlinx.android.synthetic.main.activity_show_trip.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.*


class ShowTripActivity : AppCompatActivity() {

    lateinit var trip: Trip
    var localTrip: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_trip)

        if (intent == null || intent.getStringExtra("GUID") == null)
        {
            finish()
            return
        }

        localTrip = intent!!.getBooleanExtra("LOCAL", false)?: false
        if (!localTrip)
        {
            editButt.isEnabled = false
            deleteButt.isEnabled = false
            shareButt.isEnabled = false
        }

        if (localTrip) {
            val t = DB.realm.where(Trip::class.java).equalTo(TripFields.GUID, intent.getStringExtra("GUID")!!).findFirst()
            if (t == null) {
                Toast.makeText(this, "Trip not found!", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
            trip = t

            updateTextViews()
            updateDistance()
            (mapFragment as SupportMapFragment).getMapAsync{
                it.addPolyline(PolylineOptions().width(5f).color(Color.RED).addAll(trip.path?.map { LatLng(it.lat, it.long) }))
            }
        }
        else
        {
            val context = this
            ui.launch {
                val t = bg.async {
                    try {
                        return@async API.getTrip(context, intent.getStringExtra("GUID")!!)
                    } catch (ex: UnauthenticatedException) {
                        ui.launch { Toast.makeText(context, "You dont have permision to view this trip!", Toast.LENGTH_SHORT).show() }
                    } catch (ex: ServerErrorException) {
                        ui.launch { Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show() }
                    } catch (ex: RedirectException) {
                        ui.launch { Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show() }
                    } catch (ex: Exception) {
                        ui.launch { Toast.makeText(context, "Comunication error!", Toast.LENGTH_SHORT).show() }
                    }
                    return@async null;
                }.await()

                if (t == null) {
                    finish()
                    return@launch
                }
                trip = t

                updateTextViews()
                updateDistance()

                (mapFragment as SupportMapFragment).getMapAsync{
                    it.addPolyline(PolylineOptions().width(5f).color(Color.RED).addAll(trip.path?.map { LatLng(it.lat, it.long) }))
                }
            }
        }

    }

    private fun updateDistance()
    {
        if (trip.path == null)
            return

        var distance = 0.0
        for (i in 1..(trip.path!!.size-1))
        {
            val lastLoc = Location("")
            lastLoc.latitude = trip.path!![i-1]!!.lat
            lastLoc.longitude = trip.path!![i-1]!!.long
            val thisLoc = Location("")
            thisLoc.latitude = trip.path!![i]!!.lat
            thisLoc.longitude = trip.path!![i]!!.long
            distance += thisLoc.distanceTo(lastLoc)
        }
        distanceTV.text = "Distance: %.2fkm".format(distance)
    }

    private fun updateTextViews() {
        durationTV.text = "Duration: ${trip.duration/60} min"
        nameTV.text = "Name: ${trip.name}"
        descTV.text = "Decription: ${trip.description}"
        val state = if (trip.publiclyAvailable) "Public" else "Private"
        stateTV.text = "State: ${state}"
        dateTV.text = "Date: ${trip.timeCreated}"
    }

    fun edit(view: View)
    {
        if (!localTrip)
            return;
        val context = this

        alert("Edit trip") {
            titleResource = R.string.saveTripTitle
            lateinit var name: EditText
            lateinit var desc: EditText
            lateinit var public: Switch
            customView{
                verticalLayout {
                    padding = dip(16)
                    name  = editText {
                        hintResource = R.string.nameOfTrip
                        setText(trip.name.toString())
                    }
                    desc  = editText {
                        hintResource = R.string.descriptionOfTrip
                        setText(trip.description.toString())
                    }
                    public = switch{
                        textResource = R.string.isTripPublic
                        isChecked = trip.publiclyAvailable
                    }

                }
            }
            positiveButton("Save")  {
                ui.launch {
                    DB.realm.beginTransaction()
                    trip.name = name.text.toString()
                    trip.description = desc.text.toString()
                    trip.publiclyAvailable = public.isChecked
                    DB.realm.commitTransaction()
                    updateTextViews()
                    bg.async {
                        try {
                            API.updateTrip(context, DB.realm.copyFromRealm(trip))
                        }
                        catch (ex: Exception)
                        {
                            Log.d("Retrofit:UpdateTrip", ex.message)
                        }
                    }.await()
                    it.dismiss()

                }
            }
            negativeButton("Cancel")  { it.dismiss() }
        }.show()
    }

    fun share(view: View)
    {
        if (!localTrip)
            return;

        val clipMan = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipMan.setPrimaryClip(ClipData.newPlainText("GUID",trip.guid))
        Toast.makeText(this,"Trip identificator copied to cliboard!", Toast.LENGTH_LONG).show()
        DB.realm.beginTransaction()
        trip.publiclyAvailable = true
        DB.realm.commitTransaction()
        stateTV.text = "State: ${if (trip.publiclyAvailable) "Public" else "Private"}"
    }

    fun delete(view: View)
    {
        if (!localTrip)
            return;

        alert("Are you sure you want to delete current trip?") {
            positiveButton("Yes")  {
                DB.realm.beginTransaction()
                trip.deleteFromRealm()
                DB.realm.commitTransaction()
                finish()
            }
            negativeButton("No")  {  it.dismiss() }
        }.show()
    }
}
