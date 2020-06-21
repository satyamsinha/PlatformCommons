package com.application.assignmentkotlin

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import junit.extensions.ActiveTestSuite
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    /*@Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.application.assignmentkotlin", appContext.packageName)
    }

   @get:Rule
   val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, false, true)



    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(MainActivity::class.java)
    }*/
  /*  @Test
    fun buttonShouldUpdateText() {
        onView(withId(R.id.layout_list)).perform(click())
        //onView(withId(getResourceId(""))).check(matches())
    }*/

    private fun getResourceId(s: String): Int {
        val targetContext: Context =
            InstrumentationRegistry.getInstrumentation().targetContext
        val packageName: String = targetContext.getPackageName()
        return targetContext.getResources().getIdentifier(s, "id", packageName)
    }
}
