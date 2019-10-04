package ali.khaleghi.batman.viewmodel;

import android.app.Application;
import androidx.collection.ArrayMap;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.util.Map;
import java.util.concurrent.Callable;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final Object[] params;
    private final ArrayMap<Class, Callable<? extends ViewModel>> creators;

    public ViewModelFactory(final Application application, final Object... params) {
        creators = new ArrayMap<>();

        this.application = application;
        this.params = params;

        creators.put(VideoListViewModel.class, () -> new VideoListViewModel(application));

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        Callable<? extends ViewModel> creator = creators.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class, Callable<? extends ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("Unknown model class " + modelClass);
        }
        try {
            return (T) creator.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
