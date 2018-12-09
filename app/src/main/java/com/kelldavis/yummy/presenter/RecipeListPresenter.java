package com.kelldavis.yummy.presenter;

import android.view.View;

import com.kelldavis.yummy.R;
import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.network.NetworkService;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeListPresenter implements RecipeListPresenterViewContract.Presenter {
    private final RecipeListPresenterViewContract.View mView;
    private final NetworkService mNetworkService;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public interface Callbacks {
        void recipeSelected(Recipe recipe);
    }

    public RecipeListPresenter(RecipeListPresenterViewContract.View view,
                               NetworkService networkService) {
        this.mView = view;
        this.mNetworkService = networkService;
    }

    @Override
    public void fetchRecipes() {
        Observable<ArrayList<Recipe>> retrofitObserver;

        retrofitObserver = this.mNetworkService.networkApiRequestRecipes();

        retrofitObserver.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(networkApiRecipeObserver());
    }

    private Observer<ArrayList<Recipe>> networkApiRecipeObserver() {
        return new Observer<ArrayList<Recipe>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }
            @Override
            public void onNext(ArrayList<Recipe> networkRecipeResult) {
                ArrayList<Recipe> recipeList = new ArrayList<>();
                recipeList.addAll(networkRecipeResult);
                if(mView.isActive()) {
                    mView.updateAdapter(recipeList);
                }
            }
            @Override
            public void onError(Throwable e) {
                if(mView.isActive()) {
                    mView.displaySnackbarMessage(R.string.network_error_recipes);
                }
            }
            @Override
            public void onComplete() {}
        };
    }

    @Override
    public void recipeClicked(Recipe recipe, View view) {
        ((Callbacks) view.getContext()).recipeSelected(recipe);
    }

    @Override
    public void viewDestroy() {
        mCompositeDisposable.clear();
    }
}

