package com.ccortez.desafiobrqapplicationkotlin.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import com.ccortez.desafiobrqapplicationkotlin.R
import com.ccortez.desafiobrqapplicationkotlin.service.model.Car

class RecyclerViewItemClickPutOnCartAssertion //        this.carList = cars;
    (private val expectedCount: Int) : ViewAssertion {
    private val carList: List<Car>? = null
    override fun check(
        view: View,
        noViewFoundException: NoMatchingViewException
    ) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        //        RecyclerView.Adapter adapter = (ShopCartAdapter) recyclerView.getAdapter();
        val adapter = recyclerView.adapter
        //        ((ShopCartAdapter) adapter).setCarList(carList);
        println("RecyclerViewItemClickPutOnCartAssertion adapter.getItemCount(): " + adapter!!.itemCount)
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
        val expected = 1
        Espresso.onView(ViewMatchers.withId(R.id.shop_car_list))
            .check(
                RecyclerViewItemCountAssertion(expected)
            )
    }

}