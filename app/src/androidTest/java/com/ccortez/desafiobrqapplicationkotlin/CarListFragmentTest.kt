package com.ccortez.desafiobrqapplicationkotlin

//import com.nhaarman.mockitokotlin2.whenever
import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.ccortez.desafiobrqapplicationkotlin.database.AppDatabase
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car
import com.ccortez.desafiobrqapplicationkotlin.service.repository.CarRepository
import com.ccortez.desafiobrqapplicationkotlin.utils.MyViewAction
import com.ccortez.desafiobrqapplicationkotlin.utils.RecyclerViewItemCountAssertion
import com.ccortez.desafiobrqapplicationkotlin.view.ui.CarListFragment
import com.ccortez.desafiobrqapplicationkotlin.view.ui.MainActivity
import com.ccortez.desafiobrqapplicationkotlin.view.ui.ShopCartListFragment
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarListViewModel
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
class CarListFragmentTest {

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

        db.carDao()?.clear()

    }

    @After
    fun teardown() {
        db.close()
    }

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

//        val expected = 3
//        val model = CarListViewModel(repository, application)
//
//        db.carDao()?.insert(Car(1,"Corsa Hatch", "Chevrolet", "Carro", 1, 10000, ""))
//        db.carDao()?.insert(Car(2,"Corsa Super", "Chevrolet", "Carro", 1, 10000, ""))
//        db.carDao()?.insert(Car(3,"Corsa SW", "Chevrolet", "Carro", 1, 10000, ""))

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
//        println("current Fragment: "+getVisibleFragment(_currentActivity))
//
//        onView(withId(R.id.car_list))
//            .check(
//                RecyclerViewItemCountAssertion(expected)
//            )

    }

    @Test
    fun loadingCarsRequestTest(){
//        launchFragmentInContainer<CarListFragment>()
////        onView(withId(R.id.sample_view_id)).perform(click())
//
        val appContext
                = InstrumentationRegistry.getInstrumentation().targetContext
        val expected = 19

        activityRule.launchActivity(Intent())

        activityRule.activity.supportFragmentManager
            .beginTransaction().replace(
                R.id.fragment_container,
                CarListFragment(), CarListFragment.TAG
            )
            .commitAllowingStateLoss()
        Thread.sleep(500)

        onView(withId(R.id.loading_cars))
            .check(
                matches(
                    withText(
                        appContext.getString(R.string.loading_cars)
                    )
                )
            )

        val _currentActivity :Activity = activityRule.activity

        println("current Fragment: "
                +getVisibleFragment(_currentActivity)
        )

        Thread.sleep(2000)

        println("2s")

        onView(withId(R.id.car_list))
        .check(
            RecyclerViewItemCountAssertion(expected)
        )
    }

    @Test
    fun addToCartTest(){
//        launchFragmentInContainer<CarListFragment>()
////        onView(withId(R.id.sample_view_id)).perform(click())
//
        val appContext
                = InstrumentationRegistry.getInstrumentation().targetContext
        val expected = 1

        activityRule.launchActivity(Intent())

        // call initial fragment

        activityRule.activity.supportFragmentManager
            .beginTransaction().replace(
                R.id.fragment_container,
                CarListFragment(), CarListFragment.TAG
            )
            .commitAllowingStateLoss()
        Thread.sleep(500)

        onView(withId(R.id.loading_cars))
            .check(
                matches(
                    withText(
                        appContext.getString(R.string.loading_cars)
                    )
                )
            )

        val _currentActivity :Activity = activityRule.activity
        val _carListFragment
            = getVisibleFragment(_currentActivity) as CarListFragment
        db = _carListFragment.db!!
        db.carDao()?.clear()

        println("current Fragment 1: "
                +_carListFragment
        )

        Thread.sleep(2000)

        println("2s")

        //        assertThat(adapter.getItemCount(), is(expectedCount));
//        assertEquals(expectedCount, adapter.getItemCount());
//        onView(withId(R.id.sample_view_id)).perform(click())
// R.id.car_list
//        onView(withId(view.getId())).perform(
//                RecyclerViewActions.actionOnItemAtPosition(
//                        0,
//                        MyViewAction.clickChildViewWithId(
//                                R.id.btn_colocar_carrinho
//                        )
//                ));
//        onView(withId(view.getId()))
//                .perform(
//                        RecyclerViewActions.actionOnItemAtPosition(0, click())
//                );

//        onView(withId(R.id.car_list))
//            .check(
//                RecyclerViewItemClickPutOnCartAssertion(expected)
//            )

        //        assertThat(adapter.getItemCount(), is(expectedCount));
//        assertEquals(expectedCount, adapter.getItemCount());
//        onView(withId(R.id.sample_view_id)).perform(click())
// R.id.car_list

        Thread.sleep(5000) // por conta do heroku 1a. chamada do dia

        onView(withId(R.id.car_list)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        MyViewAction.clickOnViewChild(
                                R.id.btn_colocar_carrinho
                        )
                )
        );
//        onView(withId(view.getId()))
//                .perform(
//                        RecyclerViewActions.actionOnItemAtPosition(0, click())
//                );

//        onView(withId(R.id.car_list))
//            .perform(
//                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
//                    ViewMatchers.hasDescendant(withId(R.id.btn_colocar_carrinho)),
//                    ViewActions.click()
//                )
//            )

//        onView(withId(R.id.car_list))
//            .perform(
//                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
//                    ViewMatchers.hasDescendant(withId(R.id.btn_colocar_carrinho)),
//                    ViewActions.click()
//                )
//            )

        //        assertThat(adapter.getItemCount(), is(expectedCount));
//        assertEquals(expectedCount, adapter.getItemCount());
//        onView(withId(R.id.sample_view_id)).perform(click())
// R.id.car_list
//        onView(withId(view.getId())).perform(
//                RecyclerViewActions.actionOnItemAtPosition(
//                        0,
//                        MyViewAction.clickChildViewWithId(
//                                R.id.btn_colocar_carrinho
//                        )
//                ));
//        onView(withId(view.getId()))
//                .perform(
//                        RecyclerViewActions.actionOnItemAtPosition(0, click())
//                );
//        onView(withId(view.getId()))
//                .perform(
//                    RecyclerViewActions.actionOnItem(
//                        hasDescendant(withId(R.id.btn_colocar_carrinho)),
//                        click()
//                    )
//                );
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withId(R.id.fab))
            .perform(
                ViewActions.click()
            )

//        onView(withId(R.id.shop_car_list))
//            .check(
//            RecyclerViewItemClickPutOnCartAssertion(1)
//        )

        //        assertThat(adapter.getItemCount(), is(expectedCount));
//        assertEquals(expectedCount, adapter.getItemCount());
//        onView(withId(R.id.sample_view_id)).perform(click())
// R.id.car_list
//        onView(withId(view.getId())).perform(
//                RecyclerViewActions.actionOnItemAtPosition(
//                        0,
//                        MyViewAction.clickChildViewWithId(
//                                R.id.btn_colocar_carrinho
//                        )
//                ));
//        onView(withId(view.getId()))
//                .perform(
//                        RecyclerViewActions.actionOnItemAtPosition(0, click())
//                );
//        onView(withId(view.getId()))
//                .perform(
//                    RecyclerViewActions.actionOnItem(
//                        hasDescendant(withId(R.id.btn_colocar_carrinho)),
//                        click()
//                    )
//                );
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val _shopCartListFragment = getVisibleFragment(_currentActivity) as ShopCartListFragment

        println("current Fragment 2: "
                +_shopCartListFragment
        )

        onView(withId(R.id.shop_car_list))
            .check(
                RecyclerViewItemCountAssertion(expected!!)
            )

    }

    private fun getActiveFragmentFromFrameLayout(activity: Activity): androidx.fragment.app.Fragment {
        val currentFragment
                = activityRule.activity.supportFragmentManager.findFragmentByTag(
            CarListFragment.TAG
        )
        return currentFragment!!
    }

    fun getVisibleFragment(activity: Activity): androidx.fragment.app.Fragment? {
        val fragmentManager: FragmentManager =
            activityRule.activity.supportFragmentManager
        val fragments: MutableList<androidx.fragment.app.Fragment> =
            fragmentManager?.getFragments()
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible()) return fragment
                else {
//                    return null
                }
            }
        }
        return null
    }

    fun getActiveFragment(activity: Activity): Fragment? {
        val currentFragment = activity.fragmentManager.fragments.last()
        return currentFragment
    }

}