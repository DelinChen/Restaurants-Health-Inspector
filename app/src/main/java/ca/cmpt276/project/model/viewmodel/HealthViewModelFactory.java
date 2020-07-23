package ca.cmpt276.project.model.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HealthViewModelFactory implements ViewModelProvider.Factory {
    private final HealthRepository repo;

    public HealthViewModelFactory(Context anyContext) {
        this.repo = new HealthRepository(anyContext);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HealthViewModel(repo);
    }
}
