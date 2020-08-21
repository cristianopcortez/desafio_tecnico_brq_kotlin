package com.ccortez.desafiobrqapplicationkotlin

//import com.nhaarman.mockitokotlin2.whenever
import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.content.Context
import android.content.Intent
import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import com.ccortez.desafiobrqapplicationkotlin.service.repository.CarRepository
import com.ccortez.desafiobrqapplicationkotlin.utils.RecyclerViewItemCountAssertion
import com.ccortez.desafiobrqapplicationkotlin.view.ui.CarListFragment
import com.ccortez.desafiobrqapplicationkotlin.view.ui.MainActivity
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class ShopCartListFragmentTest {

    @get:Rule
    val exceptionRule = ExpectedException.none()

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java,
        true, false)

    private lateinit var db: AppDatabase
    private lateinit var context: Context
    private lateinit var application: Application

    @Before
    fun setup() {
//        context = ApplicationProvider.getApplicationContext<Context>()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        application =
            InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext as MVVMApplication
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

//        initMocks(this)

//        TestInjector(this).inject()

//        MockitoAnnotations.initMocks(this)
    }

    @After
    fun teardown() {
        db.close()
    }

/*    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity> =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java,
            true, true) {
            protected override fun beforeActivityLaunched() {
                super.beforeActivityLaunched()
                val myApp: MVVMApplication =
                    androidx.test.InstrumentationRegistry.getTargetContext()
                        .applicationContext as MVVMApplication
                myApp.dispatchingAndroidInjector =
                    createFakeActivityInjector<Activity> {

                    }
            }
        }

    inline fun <reified T : Activity> createFakeActivityInjector(crossinline block : T.() -> Unit)
            : DispatchingAndroidInjector<Activity> {
        val injector = AndroidInjector<Activity> { instance ->
            if (instance is T) {
                instance.block()
            }
        }
        val factory = Factory<Activity> { injector }
        val map
                = mapOf(
            Pair<Class <*>,
            Provider<AndroidInjector.Factory<*>>>(
                T::class.java,
                Provider { factory }
            )
        )
        return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(
            map,
            emptyMap()
        )
//            .newInstance(map, emptyMap())
    }*/

    @Test
    fun loadingCarsTextTest() {
//        val application = Mockito.mock(MVVMApplication::class.java)
//        val application = Mockito.mock(Application::class.java)
//        val application: MVVMApplication =
//            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MVVMApplication
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val context = Mockito.mock(Context::class.java)

//        val repository: CarRepository = mock()
        val repository = CarRepository(db)

//        whenever(repository.getCarDbList(context))
//            .thenReturn(MutableLiveData<List<Car>>())

//        whenever(application.applicationContext)
//            .thenReturn(context)

        val expected = 3
        val model = CarListViewModel(repository, application)

        db.carDao()?.insert(Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""))
        db.carDao()?.insert(Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""))
        db.carDao()?.insert(Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, ""))

//        println("mutableCarList: "+mutableCarList)
//        println("mutableCarList.value: "+mutableCarList.value)

//        whenever(application.applicationContext)
//            .thenReturn(
//                context
//            )

        // instantiate your fragment
        // Context of the app under test.
        val appContext
                = InstrumentationRegistry.getInstrumentation().targetContext

//        val fragmentScenario
//                = launchFragmentInContainer<ShopCartListFragment>()

        activityRule.launchActivity(Intent())

        onView(withId(R.id.loading_cars))
            .check(
                matches(
                    withText(
                        appContext.getString(R.string.loading_cars)
                    )
                )
            )

//        onView(withId(R.id.shop_car_list))
//            .check(
//                RecyclerViewItemCountAssertion(db.carDao()!!.allCars, expected)
//            )

//        val _currentActivity :Activity = activityRule.activity
//
//        println("current Fragment: "+getActiveFragmentFromFrameLayout(_currentActivity))
//
//        onView(withId(R.id.car_list))
//            .check(
//                RecyclerViewItemCountAssertion(expected)
//            )

    }

    fun getVisibleFragment(activity: Activity): android.app.Fragment? {
        val fragmentManager: android.app.FragmentManager? =
            activity.getFragmentManager()
        val fragments: MutableList<android.app.Fragment>? =
            fragmentManager?.getFragments()
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible()) return fragment
            }
            return null
        } else
            return null
    }

    fun getActiveFragment(activity: Activity): Fragment? {
        val currentFragment = activity.fragmentManager.fragments.last()
        return currentFragment
    }

    fun getActiveFragmentFromFrameLayout(activity: Activity): Fragment? {
        val currentFragment = activity.fragmentManager.findFragmentByTag(
            CarListFragment.TAG
        )
        return currentFragment
    }

}