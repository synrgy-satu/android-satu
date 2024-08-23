package com.example.satu.mutation

import MutationVerificationFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.R
import com.example.satu.profile.ProfileFragment

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
