package cz.martinek.stepan.kayaklog

//import cz.martinek.stepan.kayaklog.database.DBHelper
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
import cz.martinek.stepan.kayaklog.model.AcquiredAchievement
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_achievements.*




class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal val info: TextView
    internal val date: TextView

    init {
        info = itemView.findViewById(R.id.achievementInfo)
        info.setTypeface(null, Typeface.BOLD)
        date = itemView.findViewById(R.id.achievementDate)
        date.setTypeface(null, Typeface.ITALIC)
    }
}
class AchievementAdapter(val results: RealmResults<AcquiredAchievement>):
        RealmRecyclerViewAdapter<AcquiredAchievement, AchievementViewHolder>(results, true) {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AchievementViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_item, parent, false)
        return AchievementViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val temp = getItem(position)
        holder.info.text = temp!!.extraInfo
        holder.date.text = temp.acquiredTime.toString()
    }

}
class AchievementsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)

        val adapter = AchievementAdapter(DB.realm.where(AcquiredAchievement::class.java).findAll())
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recycler_view.setLayoutManager(linearLayoutManager)

        recycler_view.setAdapter(adapter)
        val dividerItemDecoration = DividerItemDecoration(recycler_view.getContext(), DividerItemDecoration.VERTICAL)
        recycler_view.addItemDecoration(dividerItemDecoration)
    }
}
