package com.hdogmbh.budgettracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private FirebaseAuth auth;



    @Before
    public void setup() {
        auth = FirebaseAuth.getInstance();
    }

    @Test
    public void testFirebaseSignInWithEmailAndPassword() throws Exception{
        String email = "h22@gmail.com";
        String password = "212121";

        Task<AuthResult> signInTask = auth.signInWithEmailAndPassword(email,password);
        Tasks.await(signInTask);

        //Assertion
        if (signInTask.isSuccessful()) {
            FirebaseUser user = auth.getCurrentUser();
            assertNotNull(user);
            assertEquals("Expected UID", "obT03ebqJmai8G58hfZutU9Lcvz2", user.getUid());
        } else {
            // Authentication failed
            // Handle failure cases or fail the test
            assertEquals("Authentication failed", true, false);
        }
    }


}