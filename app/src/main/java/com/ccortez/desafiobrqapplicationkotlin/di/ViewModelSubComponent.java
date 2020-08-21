package com.ccortez.desafiobrqapplicationkotlin.di;

import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarListViewModel;
import com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarViewModel;
import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link com.ccortez.desafiobrqapplicationkotlin.viewmodel.CarViewModelFactory}.
 */
@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    CarListViewModel carListViewModel();
    CarViewModel carViewModel();
}
