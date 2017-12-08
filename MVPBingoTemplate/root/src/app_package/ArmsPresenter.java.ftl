package ${presenterPackageName};

import android.app.Application;

import ${packageName}.app.base.BasePresenter;
import ${packageName}.di.scope.ActivityScope;
import javax.inject.Inject;

import ${contractPackageName}.${pageName}Contract;



@ActivityScope
public class ${pageName}Presenter extends BasePresenter<${pageName}Contract.Model, ${pageName}Contract.View> {
 
    private Application mApplication;
    
    @Inject
    public ${pageName}Presenter (${pageName}Contract.Model model, ${pageName}Contract.View rootView , Application application) {
        super(model, rootView);

        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        this.mApplication = null;
        
    }

}
