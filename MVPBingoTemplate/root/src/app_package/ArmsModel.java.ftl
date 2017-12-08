package ${modelPackageName};

import android.app.Application;
import ${packageName}.mvp.model.http.IRepository;
import ${packageName}.app.base.BaseModel;

import ${packageName}.di.scope.ActivityScope;
import javax.inject.Inject;

import ${contractPackageName}.${pageName}Contract;


@ActivityScope
public class ${pageName}Model extends BaseModel implements ${pageName}Contract.Model{
    
    private Application mApplication;

    @Inject
    public ${pageName}Model(IRepository repository, Application application) {
        super(repository);
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

}