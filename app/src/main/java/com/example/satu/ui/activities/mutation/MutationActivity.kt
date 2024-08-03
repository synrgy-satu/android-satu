package com.example.satu.ui.activities.mutation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.R
import com.example.satu.ui.activities.mutation.MutationFragment

class MutationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mutation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MutationFragment())
                .commitNow()
        }
    }
}
