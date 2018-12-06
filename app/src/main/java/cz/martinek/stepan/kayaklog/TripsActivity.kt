package cz.martinek.stepan.kayaklog

//import cz.martinek.stepan.kayaklog.database.DBHelper
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cz.martinek.stepan.kayaklog.model.Trip
import cz.martinek.stepan.kayaklog.model.TripFields
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_achievements.*


class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal val info: TextView
    internal val date: TextView

    init {
        info = itemView.findViewById(R.id.achievementInfo)
        info.setTypeface(null, Typeface.BOLD)
        date = itemView.findViewById(R.id.achievementDate)
        date.setTypeface(null, Typeface.ITALIC)
    }
}
class TripsAdapter(val results: RealmResults<Trip>):
        RealmRecyclerViewAdapter<Trip, TripViewHolder>(results, true) {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TripViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_item, parent, false)
        return TripViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val temp = getItem(position)
        holder.info.text = temp!!.name
        holder.date.text = temp.timeCreated.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ShowTripActivity::class.java)
            intent.putExtra("GUID", temp.guid)
            intent.putExtra("LOCAL", true)
            it.context.startActivity(intent)
        }
    }

}
class TripsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trips)

        val adapter = TripsAdapter(DB.realm.where(Trip::class.java).sort(TripFields.TIME_CREATED).findAll())
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recycler_view.setLayoutManager(linearLayoutManager)

        recycler_view.setAdapter(adapter)
        val dividerItemDecoration = DividerItemDecoration(recycler_view.getContext(), DividerItemDecoration.VERTICAL)
        recycler_view.addItemDecoration(dividerItemDecoration)
    }
}
