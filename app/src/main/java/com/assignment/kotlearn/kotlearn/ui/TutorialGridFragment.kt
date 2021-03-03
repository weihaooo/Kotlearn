package com.assignment.kotlearn.kotlearn.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.*
import com.assignment.kotlearn.kotlearn.R
import kotlinx.android.synthetic.main.fragment_tutorial_grid.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TutorialGridFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment with the ProductGrid theme
        val view = inflater.inflate(R.layout.fragment_tutorial_grid, container, false)


        // Set up the RecyclerView
        view.recycler_view.setHasFixedSize(true)
        view.recycler_view.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
      //  val adapter = TutorialListAdapter( TutorialEntry.initProductEntryList(resources))
        val tutorialRecordList: List<TutorialRecord>
        val tutorial1= TutorialRecord("Intro", "10%", true, "intro")
        val tutorial2= TutorialRecord("Variables", "100%", true, "variables")
        val tutorial3= TutorialRecord("Data Type", "0%", false, "data_types")
        val tutorial4= TutorialRecord("Operators", "0%", false, "operators")
        val tutorial5= TutorialRecord("Control Flow", "0%", false, "control_flow")
        val tutorial6= TutorialRecord("Null Safety", "0%", false, "null_safety")
        tutorialRecordList=listOf(tutorial1,tutorial2,tutorial3,tutorial4,tutorial5,tutorial6)

        val adapter = TutorialListAdapter(tutorialRecordList)
        view.recycler_view.adapter = adapter
        val largePadding = 16
        val smallPadding = 4
        view.recycler_view.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))

        return view;
    }
}
