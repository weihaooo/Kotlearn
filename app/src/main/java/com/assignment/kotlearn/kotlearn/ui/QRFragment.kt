package com.assignment.kotlearn.kotlearn.ui

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.support.v4.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.assignment.kotlearn.kotlearn.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QRFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QRFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QRFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val permissionList = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (allPermissionsEnabled()) {

            } else {
                setupMultiplePermissions()
            }
        } else {
            // it must be older than Marshmallow, no need to do anything as long as
            // you have added the permission in the AndroidManifest.xml file
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_qr, container, false)
        val qrbtn: Button = view.findViewById(R.id.qrbtn)
        qrbtn.setOnClickListener {
            val intent = Intent(container!!.context, ScanQRActivity::class.java)
            container.context.startActivity(intent)
        }
        val genqrbtn: Button = view.findViewById(R.id.genqrbtn)
        genqrbtn.setOnClickListener {
            val intent = Intent(container!!.context, GenQRActivity::class.java)
            container.context.startActivity(intent)
        }
        val friendsbtn: Button = view.findViewById(R.id.friendsbtn)
        friendsbtn.setOnClickListener {
            val intent = Intent(container!!.context, FriendList::class.java)
            container.context.startActivity(intent)
        }

        // Inflate the layout for this fragment
        return view
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun allPermissionsEnabled(): Boolean {
        return permissionList.none{
            this.activity!!.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED}
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun setupMultiplePermissions() {
        val remainingPermissions = permissionList.filter {
            this.activity!!.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
        requestPermissions(remainingPermissions.toTypedArray(), 101)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissionsList: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissionsList, grantResults)

        if (requestCode == 101) {
            if (grantResults.any { it != PackageManager.PERMISSION_GRANTED }) {

                @TargetApi(Build.VERSION_CODES.M)
                if (permissionList.any{ shouldShowRequestPermissionRationale(it)}) {
                    AlertDialog.Builder(this.activity)
                            .setMessage("Press Permissions to Decide Permission Again")
                            .setPositiveButton("Permissions") { dialog, which ->
                                setupMultiplePermissions()}
                            .setNegativeButton("Cancel") {dialog, which -> dialog.dismiss()}
                            .create()
                            .show()
                }
            }
        }
    }
}
